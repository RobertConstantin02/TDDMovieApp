package com.example.tddmovieapp.data.test_doubles

import com.example.tddmovieapp.data.datasource.IMoviesRemoteDataSource
import com.example.tddmovieapp.data.model.MovieSearchDto

class MovieRemoteDatasourceFake(
    private val movieList: List<MovieSearchDto.MovieDto>?,
    private val movieException: Throwable?
) : IMoviesRemoteDataSource {
    override suspend fun getMovies(query: String): List<MovieSearchDto.MovieDto> =
        movieList ?: throw movieException ?: Throwable()
}
