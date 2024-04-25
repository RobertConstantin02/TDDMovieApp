package com.example.tddmovieapp.presentation.search

import com.example.tddmovieapp.presentation.feature.search.SearchScreenState.SearchScreenState
import com.example.tddmovieapp.presentation.feature.search.SearchScreenViewModel
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
    fun `noMovies are received`() {
        //Given
        val viewModel = SearchScreenViewModel()
        val expectedState = SearchScreenState().copy(isEmpty = true)
        val input = "not existing movie"
        //When
        viewModel.search(input)
        //Then
        assertThat(viewModel.uiState.value).isEqualTo(expectedState)
    }
}