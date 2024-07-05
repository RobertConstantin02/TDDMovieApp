package com.example.tddmovieapp.data.repository

import com.example.tddmovieapp.data.datasource.MoviesRemoteDatasource
import com.example.tddmovieapp.data.mapper.toMovieBo
import com.example.tddmovieapp.data.model.MovieError
import com.example.tddmovieapp.data.model.MovieSearchDto
import com.example.tddmovieapp.data.service.MoviesService
import com.example.tddmovieapp.data.test_doubles.MovieRemoteDatasourceFake
import com.example.tddmovieapp.domain.model.DomainError
import com.example.tddmovieapp.domain.model.DomainResource
import com.example.tddmovieapp.domain.model.MovieBo
import com.example.tddmovieapp.domain.repository.IMoviesRepository
import com.example.tddmovieapp.util.CoroutineExtension
import com.example.tddmovieapp.util.FileUtil
import com.example.tddmovieapp.util.MOVIES_FIRST_PAGE_JSON
import com.example.tddmovieapp.util.MovieUtil
import com.example.tddmovieapp.util.toMoviesService
import okhttp3.ResponseBody.Companion.toResponseBody
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.HttpURLConnection

//class MoviesRepositoryTestTest : MoviesRepositoryContractTest() {
//    override fun searchMoviesWithError(error: DomainError): IMoviesRepository {
//        val remoteDatasource = MovieRemoteDatasourceFake(null, MovieError.ServerError)
//        return MoviesRepository(remoteDatasource)
//    }
//
//    override fun searchMoviesSuccess(movieList: List<MovieBo>): IMoviesRepository {
//        val remoteDataSource = MovieRemoteDatasourceFake(movieList.map { it.toMovieDto() }, null)
//        return MoviesRepository(remoteDataSource)
//    }
//}
//
////For now has only sense here in this test
//fun MovieBo.toMovieDto() = MovieSearchDto.MovieDto(id, title, popularity, image)

@ExtendWith(CoroutineExtension::class)
class MoviesRepositoryTestTest : MoviesRepositoryContractTest() {


    override val movieList: List<MovieBo>
        get() = MovieUtil.expectedSuccessMovies.results.map { it.toMovieBo() }

    private lateinit var mockWebserver: MockWebServer
    private lateinit var service: MoviesService
    private lateinit var repository: MoviesRepository

    @BeforeEach
    fun setUp() {
        mockWebserver = MockWebServer()
        mockWebserver.start()
        service = mockWebserver.toMoviesService()
        repository = MoviesRepository(service)
    }

    @AfterEach
    fun tearDown() {
        mockWebserver.shutdown()
    }

    override fun searchMoviesWithServiceError(): IMoviesRepository {
        //mockWebserver.enqueue(MockResponse().setResponseCode(HttpURLConnection.HTTP_UNAUTHORIZED))
        val unavailableApi = object : MoviesService {
            override suspend fun getMovies(
                query: String,
                adult: Boolean
            ): Response<MovieSearchDto> {
                throw HttpException(Response.error<String>(400, "error".toResponseBody()))
            }
        }

        return MoviesRepository(unavailableApi)
    }

    override fun searchMoviesWithConnectivityError(): IMoviesRepository {
        val offlineApi = object : MoviesService {
            override suspend fun getMovies(
                query: String,
                adult: Boolean
            ): Response<MovieSearchDto> {
                throw IOException("error")
            }
        }
        return MoviesRepository(offlineApi)
    }

    override fun searchMoviesSuccess(movieList: List<MovieBo>): IMoviesRepository {
        mockWebserver.enqueue(
            MockResponse().setResponseCode(200)
                .setBody(FileUtil.getJson(MOVIES_FIRST_PAGE_JSON).orEmpty())
        )
        return repository
    }
}

//For now has only sense here in this test
fun MovieBo.toMovieDto() = MovieSearchDto.MovieDto(id, title, popularity, image)


class MoviesRepository(private val api: MoviesService) : IMoviesRepository {
    override suspend fun searchMovies(query: String): DomainResource<List<MovieBo>> =
        try {
            val result = api.getMovies(query)
            if (result.isSuccessful && result.body() != null)
                DomainResource.success(result.body()!!.results.map { it.toMovieBo() })
            else DomainResource.success(emptyList())
        } catch (e: Throwable) {
            when (e) {
                is IOException -> DomainResource.error(DomainError.ConnectivityError)
                is HttpException -> DomainResource.error(DomainError.ServerError)
                else -> DomainResource.error(DomainError.Unknown)
            }
        }
}