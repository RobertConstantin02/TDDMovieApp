package com.example.tddmovieapp.domain.test_doubles

import com.example.tddmovieapp.domain.model.DomainError
import com.example.tddmovieapp.domain.usecase.SearchMoviesUseCase
import com.example.tddmovieapp.presentation.model.MovieVO

class SearchMoviesUseCaseImplSuccessStub : SearchMoviesUseCase() {
    override operator fun invoke(
        query: String,
        success: (listMovies: List<MovieVO>) -> Unit,
        empty: () -> Unit,
        error: (DomainError) -> Unit
    ) {
        val movies = listOf(
            MovieVO(131, "Iron Man", 4.5, "imageUrl"),
            MovieVO(4532, "Marvel: Avangers", 4.1, "imageUrl1"),
            MovieVO(5675, "Marvel: Black Panther", 5.0, "imageUrl2")
        )

        val searchedMovies = movies.filter { movie ->
            movie.title.contains(query, true)
        }

        success(searchedMovies)
    }
}