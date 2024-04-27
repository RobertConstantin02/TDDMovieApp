package com.example.tddmovieapp.presentation.search

import com.example.tddmovieapp.presentation.feature.search.SearchScreenEvent
import com.example.tddmovieapp.presentation.feature.search.SearchScreenState
import com.example.tddmovieapp.presentation.feature.search.SearchScreenViewModel
import com.example.tddmovieapp.presentation.feature.search.test_doubles.SearchMoviesUseCaseImplSuccessStub
import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class SearchViewModelStateTest {

    private lateinit var viewModel: SearchScreenViewModel

    @BeforeEach
    fun setUp() {
        viewModel = SearchScreenViewModel(SearchMoviesUseCaseImplSuccessStub())
    }

    @Test
    fun `initial state is the default one`() {
        //Given
        val expected = SearchScreenState()
        //Then
        assertThat(viewModel.uiState.value).isEqualTo(expected)
    }

    @Test
    fun `search query is updated`() {
        //Given
        val expected = "::irrelevant::"
        //when
        viewModel.onEvent(SearchScreenEvent.OnUpdateQuery(expected))
        //Then
        assertThat(viewModel.queryState).isEqualTo(expected)
    }
}