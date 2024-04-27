package com.example.tddmovieapp.domain.usecase

import com.example.tddmovieapp.domain.model.DomainError
import com.example.tddmovieapp.presentation.model.MovieVO

abstract class SearchMoviesUseCase {
    abstract operator fun invoke(
        query: String,
        success: (listMovies: List<MovieVO>) -> Unit,
        empty: () -> Unit,
        error: (DomainError) -> Unit
    )
}