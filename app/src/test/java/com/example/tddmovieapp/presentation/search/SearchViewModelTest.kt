package com.example.tddmovieapp.presentation.search

import com.example.tddmovieapp.presentation.feature.search.SearchScreenState.SearchScreenState
import com.example.tddmovieapp.presentation.feature.search.SearchScreenViewModel
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
        val viewModel = SearchScreenViewModel()
        val expectedState = SearchScreenState().copy(isEmpty = true)
        val input = "not existing movie"
        //When
        viewModel.search(input)
        //Then
        assertThat(viewModel.uiState.value).isEqualTo(expectedState)
    }

    @Test
    fun `when search success and one Movie received`() {
        val movieListWithSingleItem = listOf<MovieVO>(MovieVO(1, "Iron Man", 4.5, "imageUrl"))
        val expectedState = SearchScreenState().copy(isLoading = false, success = movieListWithSingleItem)
        val viewModel = SearchScreenViewModel()
        val input = "iron"

        viewModel.search(input)

        assertThat(viewModel.uiState.value).isEqualTo(expectedState)
    }

    @Test
    fun `when search success and many Movies received`() {
        val viewModel = SearchScreenViewModel()
        val movieListWithManyItems = listOf<MovieVO>(
            MovieVO(1, "Avangers", 4.1, "imageUrl1"),
            MovieVO(1, "Black Panther", 5.0, "imageUrl2")
        )

        val input = "Marvel"
        viewModel.search(input)

        val expectedState = SearchScreenState().copy(isLoading = false, success = movieListWithManyItems)
        assertThat(viewModel.uiState.value).isEqualTo(expectedState)
    }
}