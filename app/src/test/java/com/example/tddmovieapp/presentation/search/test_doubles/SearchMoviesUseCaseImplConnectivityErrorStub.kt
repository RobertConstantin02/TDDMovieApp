package com.example.tddmovieapp.presentation.search.test_doubles

import com.example.tddmovieapp.domain.model.DomainSearchError
import com.example.tddmovieapp.domain.usecase.SearchMoviesUseCase
import com.example.tddmovieapp.presentation.model.MovieVO

class SearchMoviesUseCaseImplConnectivityErrorStub : SearchMoviesUseCase() {
    override fun invoke(
        query: String,
        success: (listMovies: List<MovieVO>) -> Unit,
        empty: () -> Unit,
        error: (DomainSearchError) -> Unit
    ) {
        error(DomainSearchError.ConnectivityError)
    }
}
