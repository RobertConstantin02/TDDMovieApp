package com.example.tddmovieapp.data.repository

import com.example.tddmovieapp.data.model.MovieSearchDto
import com.example.tddmovieapp.data.service.MoviesService
import com.example.tddmovieapp.domain.repository.IMoviesRepository
import com.example.tddmovieapp.util.CoroutineExtension
import com.example.tddmovieapp.util.toMoviesService
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
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

    private lateinit var mockWebserver: MockWebServer
    private lateinit var service: MoviesService

    @BeforeEach
    fun setUp() {
        mockWebserver = MockWebServer()
        mockWebserver.start()
        service = mockWebserver.toMoviesService()
    }

    @AfterEach
    fun tearDown() {
        mockWebserver.shutdown()
    }

    override fun searchMoviesWithServiceError(): IMoviesRepository {
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

    override fun searchMoviesSuccess(movieList: List<MovieSearchDto.MovieDto>): IMoviesRepository {
        mockWebserver.dispatcher = CustomDispatcher(movieList)
        return MoviesRepository(service)
    }
}

private class CustomDispatcher(val movieList: List<MovieSearchDto.MovieDto>): Dispatcher() {
    override fun dispatch(request: RecordedRequest): MockResponse {
        val searchedMovies = buildString {
            append("{")
            append(""""results" : ${movieList.map { it.toResponseEntity() }}""")
            append("}")
        }
        return MockResponse().setResponseCode(200).setBody(searchedMovies)
    }
}

private fun MovieSearchDto.MovieDto.toResponseEntity(): String {
    val jsonObject = buildJsonObject {
        put("id", id)
        put("title", title)
        put("popularity", popularity)
        put("poster_path", image)
    }
    return jsonObject.toString()
}