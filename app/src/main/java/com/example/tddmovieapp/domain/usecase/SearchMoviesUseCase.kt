package com.example.tddmovieapp.domain.usecase

import com.example.tddmovieapp.domain.model.DomainError
import com.example.tddmovieapp.domain.model.MovieBo
import com.example.tddmovieapp.presentation.model.MovieVO

abstract class SearchMoviesUseCase {
    abstract operator fun invoke(
        query: String,
        success: (listMovies: List<MovieBo>) -> Unit,
        empty: () -> Unit,
        error: (DomainError) -> Unit
    )
}