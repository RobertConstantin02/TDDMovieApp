package com.example.tddmovieapp.data.datasource

import com.example.tddmovieapp.data.model.MovieSearchDto

interface IMoviesRemoteDataSource {
    fun getMovies(query: String): List<MovieSearchDto.MovieDto>
}
