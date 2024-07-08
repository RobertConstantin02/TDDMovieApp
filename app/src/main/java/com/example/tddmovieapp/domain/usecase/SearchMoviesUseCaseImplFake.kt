package com.example.tddmovieapp.domain.usecase

import com.example.tddmovieapp.domain.model.DomainError
import com.example.tddmovieapp.domain.model.DomainResource
import com.example.tddmovieapp.domain.model.MovieBo
import com.example.tddmovieapp.domain.usecase.SearchMoviesUseCase

// TODO: This is a fake because is not returning a static data. It can be emptuy or success data
class SearchMoviesUseCaseImplFake(
    private val domainResource: DomainResource.Success<List<MovieBo>>
) : SearchMoviesUseCase() {
    private var domainError: DomainError? = null

    override suspend operator fun invoke(
        query: String,
        success: (listMovies: List<MovieBo>) -> Unit,
        empty: () -> Unit,
        error: (DomainError) -> Unit
    ) {
        domainError?.let { error(it) }
        if (domainResource.data.isNotEmpty()) success(domainResource.data)
        else empty()
    }

    fun setError(error: DomainError) {
        domainError = error
    }
}