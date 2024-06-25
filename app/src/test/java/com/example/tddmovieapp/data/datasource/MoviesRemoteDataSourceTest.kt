package com.example.tddmovieapp.data.datasource

import com.example.tddmovieapp.data.model.MovieSearchDto
import com.example.tddmovieapp.data.service.MoviesService
import com.example.tddmovieapp.util.MovieUtil
import com.example.tddmovieapp.util.toRickAndMortyService
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.BeforeEach

class MoviesRemoteDataSourceTest: MoviesRemoteDataSourceContractTest() {

    private lateinit var mockWebserver: MockWebServer
    private lateinit var service: MoviesService

    @BeforeEach
    fun setUp() {
        mockWebserver = MockWebServer()
        mockWebserver.start()
        service = mockWebserver.toRickAndMortyService()
    }

    override val expectedMovieList: List<MovieSearchDto.MovieDto>
        get() = MovieUtil.expectedSuccessMovies.results

    override fun successMovies(): IMoviesRemoteDataSource {
        TODO("Not yet implemented")
    }

    override fun successMoviesEmpty(): IMoviesRemoteDataSource {
        TODO("Not yet implemented")
    }

    override fun errorMovies(): IMoviesRemoteDataSource {
        TODO("Not yet implemented")
    }
}

class MoviesRemoteDatasource(private val api: MoviesService): IMoviesRemoteDataSource {
    override fun getMovies(): List<MovieSearchDto.MovieDto> {

    }
}