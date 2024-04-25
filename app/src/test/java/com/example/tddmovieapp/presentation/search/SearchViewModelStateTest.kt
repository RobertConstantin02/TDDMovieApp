package com.example.tddmovieapp.presentation.search

import com.example.tddmovieapp.presentation.feature.search.SearchScreenState.SearchScreenState
import com.example.tddmovieapp.presentation.feature.search.SearchScreenViewModel
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.jupiter.api.Test

class SearchViewModelStateTest {

    private lateinit var viewModel: SearchScreenViewModel

    @Before
    fun setUp() {
        viewModel = SearchScreenViewModel()
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
        viewModel.updateSearchQuery(expected)
        //Then
        assertThat(viewModel.queryState).isEqualTo(expected)
    }
}