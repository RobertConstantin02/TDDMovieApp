package com.example.tddmovieapp.domain.usecase

import com.example.tddmovieapp.presentation.model.MovieVO

abstract class ISearchMoviesUseCase {
    abstract operator fun invoke(
        query: String,
        success: (listMovies: List<MovieVO>) -> Unit,
        empty: () -> Unit
    )
}