package com.example.tddmovieapp.data.datasource

import com.example.tddmovieapp.data.model.MovieError
import com.example.tddmovieapp.data.model.MovieSearchDto
import com.example.tddmovieapp.data.service.MoviesService
import com.example.tddmovieapp.util.FileUtil
import com.example.tddmovieapp.util.MOVIES_FIRST_PAGE_JSON
import com.example.tddmovieapp.util.MovieUtil
import com.example.tddmovieapp.util.toMoviesService
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.IOException
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import retrofit2.HttpException
import java.net.HttpURLConnection

class MoviesRemoteDataSourceTest: MoviesRemoteDataSourceContractTest() {

    private lateinit var mockWebserver: MockWebServer
    private lateinit var service: MoviesService
    private lateinit var datasource: MoviesRemoteDatasource

    @BeforeEach
    fun setUp() {
        mockWebserver = MockWebServer()
        mockWebserver.start()
        service = mockWebserver.toMoviesService()
        datasource = MoviesRemoteDatasource(service)
    }

    @AfterEach
    fun tearDown() {
        mockWebserver.shutdown()
    }

    override val expectedMovieList: List<MovieSearchDto.MovieDto>
        get() = MovieUtil.expectedSuccessMovies.results

    override val query: String
        get() = "marvel"

    override fun successMovies(): IMoviesRemoteDataSource {
        mockWebserver.enqueue(MockResponse().setResponseCode(200).setBody(FileUtil.getJson(MOVIES_FIRST_PAGE_JSON).orEmpty()))
        return datasource
    }

    override fun successMoviesEmpty(): IMoviesRemoteDataSource {
        mockWebserver.enqueue(MockResponse().setResponseCode(204).setBody(""))
        return datasource
    }

    override fun errorMovies(): IMoviesRemoteDataSource {
        mockWebserver.enqueue(MockResponse().setResponseCode(HttpURLConnection.HTTP_UNAUTHORIZED))
        return datasource
    }
}

class MoviesRemoteDatasource(private val api: MoviesService): IMoviesRemoteDataSource {
    override suspend fun getMovies(query: String): List<MovieSearchDto.MovieDto> {
        try {
            val result = api.getMovies(query)
            return if (result.isSuccessful && result.body() != null)  result.body()!!.results
            else emptyList()
        }catch (e: Throwable) {
            when(e) {
                is IOException -> throw MovieError.ConnectivityError
                is HttpException -> throw MovieError.ServerError
                else -> throw MovieError.Unknown
            }
        }
    }
}