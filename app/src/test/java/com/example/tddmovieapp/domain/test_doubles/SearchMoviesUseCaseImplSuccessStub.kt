package com.example.tddmovieapp.domain.test_doubles

import com.example.tddmovieapp.domain.model.DomainError
import com.example.tddmovieapp.domain.model.DomainResource
import com.example.tddmovieapp.domain.model.MovieBo
import com.example.tddmovieapp.domain.usecase.SearchMoviesUseCase
import com.example.tddmovieapp.presentation.model.MovieVO

class SearchMoviesUseCaseImplSuccessStub(val domainResource: DomainResource.Success<List<MovieBo>>) : SearchMoviesUseCase() {
    override operator fun invoke(
        query: String,
        success: (listMovies: List<MovieBo>) -> Unit,
        empty: () -> Unit,
        error: (DomainError) -> Unit
    ) {
        if (domainResource.data.isNotEmpty()) success(domainResource.data)
        else empty()
    }
}