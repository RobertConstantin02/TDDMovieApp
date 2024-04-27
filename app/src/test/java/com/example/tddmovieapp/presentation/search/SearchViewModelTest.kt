package com.example.tddmovieapp.presentation.search

import com.example.tddmovieapp.presentation.feature.search.SearchScreenEvent
import com.example.tddmovieapp.presentation.feature.search.SearchScreenState
import com.example.tddmovieapp.presentation.feature.search.SearchScreenViewModel
import com.example.tddmovieapp.presentation.feature.search.test_doubles.SearchMoviesUseCaseImplSuccessStub
import com.example.tddmovieapp.presentation.model.MovieVO
import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test

class SearchViewModelTest {
    //Z
    //O
    //M
    //B
    //I
    //E


    @Test
    fun `when search success and no Movies are received`() {
        //Given
        val viewModel = SearchScreenViewModel(SearchMoviesUseCaseImplSuccessStub())
        val expectedState = SearchScreenState().copy(isLoading = false, isEmpty = true)
        val input = "not existing movie"
        //When
        viewModel.onEvent(SearchScreenEvent.OnUpdateQuery(input))
        viewModel.onEvent(SearchScreenEvent.OnSearchMovies)
        //Then
        assertThat(viewModel.uiState.value).isEqualTo(expectedState)
    }

    @Test
    fun `when search success and one Movie received`() {
        val movieListWithSingleItem = listOf<MovieVO>(MovieVO(131, "Iron Man", 4.5, "imageUrl"))
        val expectedState = SearchScreenState().copy(isLoading = false,  isEmpty = false, success = movieListWithSingleItem)
        val viewModel = SearchScreenViewModel(SearchMoviesUseCaseImplSuccessStub())
        val input = "iron"

        viewModel.onEvent(SearchScreenEvent.OnUpdateQuery(input))
        viewModel.onEvent(SearchScreenEvent.OnSearchMovies)

        assertThat(viewModel.uiState.value).isEqualTo(expectedState)
    }

    @Test
    fun `when search success and many Movies received`() {
        val viewModel = SearchScreenViewModel(SearchMoviesUseCaseImplSuccessStub())
        val movieListWithManyItems = listOf<MovieVO>(
            MovieVO(4532, "Marvel: Avangers", 4.1, "imageUrl1"),
            MovieVO(5675, "Marvel: Black Panther", 5.0, "imageUrl2")
        )

        val input = "Marvel"
        viewModel.onEvent(SearchScreenEvent.OnUpdateQuery(input))
        viewModel.onEvent(SearchScreenEvent.OnSearchMovies)

        val expectedState = SearchScreenState().copy(isLoading = false,  isEmpty = false, success = movieListWithManyItems)
        assertThat(viewModel.uiState.value).isEqualTo(expectedState)
    }
}