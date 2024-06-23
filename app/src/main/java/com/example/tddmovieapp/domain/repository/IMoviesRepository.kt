package com.example.tddmovieapp.domain.repository

import com.example.tddmovieapp.domain.model.DomainResource
import com.example.tddmovieapp.domain.model.MovieBo

interface IMoviesRepository {
    suspend fun searchMovies(query: String): DomainResource<List<MovieBo>>
}