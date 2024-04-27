package com.example.tddmovieapp.domain.test_doubles

import com.example.tddmovieapp.domain.model.DomainError
import com.example.tddmovieapp.domain.usecase.SearchMoviesUseCase
import com.example.tddmovieapp.presentation.model.MovieVO

class SearchMoviesUseCaseImplEmptyStub : SearchMoviesUseCase() {

    override fun invoke(
        query: String,
        success: (listMovies: List<MovieVO>) -> Unit,
        empty: () -> Unit,
        error: (DomainError) -> Unit
    ) {
        empty()
    }
}
