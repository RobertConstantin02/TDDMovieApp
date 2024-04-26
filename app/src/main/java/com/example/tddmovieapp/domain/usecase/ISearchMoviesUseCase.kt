package com.example.tddmovieapp.domain.usecase

import com.example.tddmovieapp.presentation.model.MovieVO

interface ISearchMoviesUseCase {
    operator fun invoke(
        query: String,
        success: (listMovies: List<MovieVO>) -> Unit,
        empty: () -> Unit
    )
}