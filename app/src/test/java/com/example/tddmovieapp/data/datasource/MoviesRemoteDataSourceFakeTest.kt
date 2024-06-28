package com.example.tddmovieapp.data.datasource

import com.example.tddmovieapp.data.model.MovieSearchDto
import com.example.tddmovieapp.data.test_doubles.MovieRemoteDatasourceFake

class MoviesRemoteDataSourceFakeTest : MoviesRemoteDataSourceContractTest() {

    override val expectedMovieList = listOf(
        MovieSearchDto.MovieDto(4532, "Marvel: Avangers", 4.1, "imageUrl1"),
        MovieSearchDto.MovieDto(5675, "Marvel: Black Panther", 5.0, "imageUrl2"),
    )

    override val query: String
        get() = "marvel"

    override fun successMovies(): IMoviesRemoteDataSource =
        MovieRemoteDatasourceFake(expectedMovieList, null)
    override fun successMoviesEmpty(): IMoviesRemoteDataSource =
        MovieRemoteDatasourceFake(emptyList(), null)
    override fun errorMovies(): IMoviesRemoteDataSource =
        MovieRemoteDatasourceFake(null, Throwable())

}