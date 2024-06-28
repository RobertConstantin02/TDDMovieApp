package com.example.tddmovieapp.data.datasource

import com.example.tddmovieapp.data.model.MovieSearchDto

interface IMoviesRemoteDataSource {
    suspend fun getMovies(query: String): List<MovieSearchDto.MovieDto>
}
