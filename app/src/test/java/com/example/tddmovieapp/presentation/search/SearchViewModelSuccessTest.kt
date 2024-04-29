package com.example.tddmovieapp.presentation.search

import com.example.tddmovieapp.domain.model.DomainResource
import com.example.tddmovieapp.domain.model.MovieBo
import com.example.tddmovieapp.presentation.feature.search.SearchScreenEvent
import com.example.tddmovieapp.presentation.feature.search.SearchScreenState
import com.example.tddmovieapp.presentation.feature.search.SearchScreenViewModel
import com.example.tddmovieapp.domain.test_doubles.SearchMoviesUseCaseImplSuccessFake
import com.example.tddmovieapp.presentation.feature.util.QueryValidator
import com.example.tddmovieapp.presentation.mapper.toMovieVo
import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class SearchViewModelSuccessTest {

    private lateinit var viewModel: SearchScreenViewModel

    @BeforeEach
    fun setUp() {
        viewModel = SearchScreenViewModel(
            SearchMoviesUseCaseImplSuccessFake(
                DomainResource.success(emptyList())
            ), QueryValidator()
        )
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

    @Test
    fun `initial state is the default one`() {
        //Given
        val expected = SearchScreenState()
        //Then
        assertThat(viewModel.uiState.value).isEqualTo(expected)
    }

    //Zero
    //One
    //Many
    //Boundary
    //Interface
    //Exception

    @Test
    fun `when search success and no Movies are received`() {
        //Given
        val expectedState = SearchScreenState().copy(isLoading = false, isEmpty = true)
        val input = "not existing movie"
        viewModel = SearchScreenViewModel(
            SearchMoviesUseCaseImplSuccessFake(
                DomainResource.success(emptyList())

            ), QueryValidator()
        )
        //When
        viewModel.onEvent(SearchScreenEvent.OnUpdateQuery(input))
        viewModel.onEvent(SearchScreenEvent.OnSearchMovies)
        //Then
        assertThat(viewModel.uiState.value).isEqualTo(expectedState)
    }

    @Test
    fun `when search success and one Movie received`() {
        val movieListWithSingleItem = listOf<MovieBo>(MovieBo(131, "Iron Man", 4.5, "imageUrl"))
        val expectedState = SearchScreenState().copy(isLoading = false,  isEmpty = false, success = movieListWithSingleItem.map { it.toMovieVo() })
        viewModel = SearchScreenViewModel(
            SearchMoviesUseCaseImplSuccessFake(
                DomainResource.success(movieListWithSingleItem)
            ), QueryValidator()
        )
        val input = "iron"

        viewModel.onEvent(SearchScreenEvent.OnUpdateQuery(input))
        viewModel.onEvent(SearchScreenEvent.OnSearchMovies)

        assertThat(viewModel.uiState.value).isEqualTo(expectedState)
    }

    @Test
    fun `when search success and many Movies received`() {
        val movieListWithManyItems = listOf<MovieBo>(
            MovieBo(4532, "Marvel: Avangers", 4.1, "imageUrl1"),
            MovieBo(5675, "Marvel: Black Panther", 5.0, "imageUrl2")
        )
        val expectedState = SearchScreenState().copy(isLoading = false,  isEmpty = false, success = movieListWithManyItems.map { it.toMovieVo() })
        viewModel = SearchScreenViewModel(SearchMoviesUseCaseImplSuccessFake(
            DomainResource.success(movieListWithManyItems),
        ), QueryValidator())

        val input = "Marvel"
        viewModel.onEvent(SearchScreenEvent.OnUpdateQuery(input))
        viewModel.onEvent(SearchScreenEvent.OnSearchMovies)

        assertThat(viewModel.uiState.value).isEqualTo(expectedState)
    }
}