package com.example.tddmovieapp.domain.usecase

import com.example.tddmovieapp.domain.di.QMoviesRepository
import com.example.tddmovieapp.domain.model.DomainError
import com.example.tddmovieapp.domain.model.DomainResource
import com.example.tddmovieapp.domain.model.MovieBo
import com.example.tddmovieapp.domain.repository.IMoviesRepository
import javax.inject.Inject

// TODO: extract into a repositoryFake, make abstraction, then test the repository fake and extract contract test to implement the real one.  
class SearchMoviesUseCaseImpl @Inject constructor(
    @QMoviesRepository private val moviesRepositoryFake: IMoviesRepository
) : SearchMoviesUseCase() {
    override suspend fun invoke(
        query: String,
        success: (listMovies: List<MovieBo>) -> Unit,
        empty: () -> Unit,
        error: (DomainError) -> Unit
    ) {
        when (val result = moviesRepositoryFake.searchMovies(query)) {
            is DomainResource.Success -> {
                if (result.data.isNotEmpty()) success(result.data)
                else empty()
            }

            is DomainResource.Error -> error(result.error)
        }
    }
}