package com.example.tddmovieapp.presentation.feature.search.test_doubles

import com.example.tddmovieapp.domain.usecase.ISearchMoviesUseCase
import com.example.tddmovieapp.presentation.model.MovieVO

class SearchMoviesUseCaseStub : ISearchMoviesUseCase {
    override operator fun invoke(
        query: String,
        success: (listMovies: List<MovieVO>) -> Unit,
        empty: () -> Unit
    ) {
        val movies = listOf(
            MovieVO(131, "Iron Man", 4.5, "imageUrl"),
            MovieVO(4532, "Marvel: Avangers", 4.1, "imageUrl1"),
            MovieVO(5675, "Marvel: Black Panther", 5.0, "imageUrl2")
        )

        val searchedMovies = movies.filter { movie ->
            movie.title.contains(query, true)
        }

        if (searchedMovies.isNotEmpty()) {
            success(searchedMovies)
        } else {
            empty()
        }
    }
}