package com.example.tddmovieapp.presentation.search

import com.example.tddmovieapp.presentation.feature.search.SearchError
import com.example.tddmovieapp.presentation.feature.search.SearchScreenEvent
import com.example.tddmovieapp.presentation.feature.search.SearchScreenViewModel
import com.example.tddmovieapp.presentation.search.test_doubles.SearchMoviesUseCaseImplConnectivityErrorStub
import com.example.tddmovieapp.presentation.search.test_doubles.SearchMoviesUseCaseImplServerErrorStub
import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test

class SearchViewModelErrorTest {

    @Test
    fun `when search and server error`() {
        //Given
        val input = "Iron Main"
        val viewModel = SearchScreenViewModel(SearchMoviesUseCaseImplServerErrorStub())
        //When
        viewModel.onEvent(SearchScreenEvent.OnUpdateQuery(input))
        viewModel.onEvent(SearchScreenEvent.OnSearchMovies)
        //Then
        assertThat(viewModel.uiState.value.error).isNotNull()
        assertThat(viewModel.uiState.value.error).isInstanceOf(SearchError.ServerError::class.java)
    }

    @Test
    fun `when search and connectivity error`() {

        //Given
        val input = "Iron Man"
        //When
        val viewModel = SearchScreenViewModel(SearchMoviesUseCaseImplConnectivityErrorStub())
        viewModel.onEvent(SearchScreenEvent.OnUpdateQuery(input))
        viewModel.onEvent(SearchScreenEvent.OnSearchMovies)
        //Then
        assertThat(viewModel.uiState.value.error).isNotNull()
        assertThat(viewModel.uiState.value.error).isInstanceOf(SearchError.ConnectivityError::class.java)
    }
}