package com.example.tddmovieapp.presentation.search

import com.example.tddmovieapp.domain.model.DomainResource
import com.example.tddmovieapp.domain.model.MovieBo
import com.example.tddmovieapp.presentation.feature.search.SearchError
import com.example.tddmovieapp.presentation.feature.search.SearchScreenEvent
import com.example.tddmovieapp.presentation.feature.search.SearchScreenViewModel
import com.example.tddmovieapp.domain.test_doubles.SearchMoviesUseCaseImplConnectivityErrorStub
import com.example.tddmovieapp.domain.test_doubles.SearchMoviesUseCaseImplServerErrorStub
import com.example.tddmovieapp.domain.test_doubles.SearchMoviesUseCaseImplSuccessFake
import com.example.tddmovieapp.presentation.feature.search.SearchScreenState
import com.example.tddmovieapp.presentation.feature.util.QueryValidator
import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class SearchViewModelErrorTest {

    @Test
    fun `when search and server error`() {
        //Given
        val input = "Iron Main"
        val viewModel = SearchScreenViewModel(
            SearchMoviesUseCaseImplServerErrorStub(),
            QueryValidator()
        )
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
        val viewModel = SearchScreenViewModel(
            SearchMoviesUseCaseImplConnectivityErrorStub(),
            QueryValidator()
        )
        viewModel.onEvent(SearchScreenEvent.OnUpdateQuery(input))
        viewModel.onEvent(SearchScreenEvent.OnSearchMovies)
        //Then
        assertThat(viewModel.uiState.value.error).isNotNull()
        assertThat(viewModel.uiState.value.error).isInstanceOf(SearchError.ConnectivityError::class.java)
    }

    //--------- query validation

    //this query que can check and you will se that is giving results tact are not very related to
    //what we are looking for (marvel movies). So is better to filter out the bad formatted queries
    //to prevent making unnecessary queries to the backend
    @ParameterizedTest
    @CsvSource(
        "'mar-vel', true",
        "'[marvel]', true",
        "'*marvel', true"
    )
    fun `given a query with les than 3 characters, when search, then isBadFormatQuery true`(
        query: String, isQueryFormatError: Boolean
    ) {
        //Given
        val movieListWithManyItems = listOf<MovieBo>(
            MovieBo(5675, "A Velha e o mar... e batedeira", 5.0, "imageUrl2"),
            MovieBo(67856, "Terra Nova, Mar Velho", 3.0, "imageUrl3")
        )
        val viewModel = SearchScreenViewModel(
            SearchMoviesUseCaseImplSuccessFake(
                DomainResource.success(movieListWithManyItems)
            ), QueryValidator()
        )
        //When
        viewModel.onEvent(SearchScreenEvent.OnUpdateQuery(query))
        viewModel.onEvent(SearchScreenEvent.OnSearchMovies)
        //Then
        val expected = SearchScreenState().copy(isQueryFormatError = isQueryFormatError)
        assertThat(viewModel.uiState.value).isEqualTo(expected)
    }

    //good query and then malformed query
}