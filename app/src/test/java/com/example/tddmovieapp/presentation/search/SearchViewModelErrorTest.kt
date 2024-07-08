package com.example.tddmovieapp.presentation.search

import com.example.tddmovieapp.domain.model.DomainError
import com.example.tddmovieapp.domain.model.DomainResource
import com.example.tddmovieapp.domain.model.MovieBo
import com.example.tddmovieapp.presentation.feature.search.SearchError
import com.example.tddmovieapp.presentation.feature.search.SearchScreenEvent
import com.example.tddmovieapp.presentation.feature.search.SearchScreenViewModel
import com.example.tddmovieapp.domain.test_doubles.SearchMoviesUseCaseImplConnectivityErrorStub
import com.example.tddmovieapp.domain.test_doubles.SearchMoviesUseCaseImplServerErrorStub
import com.example.tddmovieapp.domain.test_doubles.SearchMoviesUseCaseImplFake
import com.example.tddmovieapp.presentation.feature.search.SearchScreenState
import com.example.tddmovieapp.presentation.feature.util.Validator
import com.example.tddmovieapp.presentation.mapper.toMovieVo
import com.example.tddmovieapp.util.CoroutineExtension
import com.example.tddmovieapp.util.observeFlow
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

@ExtendWith(CoroutineExtension::class)
@OptIn(ExperimentalCoroutinesApi::class)
class SearchViewModelErrorTest {

    private val backgroundTestDispatcher = UnconfinedTestDispatcher()

    @Test
    fun `when search and server error`() {
        //Given
        val viewModel = SearchScreenViewModel(
            SearchMoviesUseCaseImplServerErrorStub(),
            QueryValidatorFake(),
            backgroundTestDispatcher
        )
        //When
        viewModel.onEvent(SearchScreenEvent.OnSearchMovies)
        //Then
        assertThat(viewModel.uiState.value.error).isNotNull()
        assertThat(viewModel.uiState.value.error).isInstanceOf(SearchError.ServerError::class.java)
    }

    @Test
    fun `when search and connectivity error`() {
        //Given
        val viewModel = SearchScreenViewModel(
            SearchMoviesUseCaseImplConnectivityErrorStub(),
            QueryValidatorFake(),
            backgroundTestDispatcher
        )
        //When
        viewModel.onEvent(SearchScreenEvent.OnSearchMovies)
        //Then
        assertThat(viewModel.uiState.value.error).isNotNull()
        assertThat(viewModel.uiState.value.error).isInstanceOf(SearchError.ConnectivityError::class.java)
    }

    //--------- query validation

    //this query can check and you will se that is giving results tact are not very related to
    //what we are looking for (marvel movies). So is better to filter out the bad formatted queries
    //to prevent making unnecessary queries to the backend
    @ParameterizedTest
    @CsvSource(
        "'mar-vel', false",
        "'[marvel]', false",
        "'*marvel', false",
        "'', false",
        "'   ', false"
    )
    fun `given a query with les than 3 characters or special characters, when search, then isQueryFormatError true`(
        query: String, isQueryFormatError: Boolean
    ) {
        //Given
        val movieListWithManyItems = listOf<MovieBo>(
            MovieBo(5675, "A Velha e o mar... e batedeira", 5.0, "imageUrl2"),
            MovieBo(67856, "Terra Nova, Mar Velho", 3.0, "imageUrl3")
        )
        val viewModel = SearchScreenViewModel(
            SearchMoviesUseCaseImplFake(
                DomainResource.success(movieListWithManyItems)
            ),
            QueryValidatorFake().also {
                it.isValidQuery = isQueryFormatError
            },
            backgroundTestDispatcher
        )
        //When
        viewModel.onEvent(SearchScreenEvent.OnUpdateQuery(query))
        viewModel.onEvent(SearchScreenEvent.OnSearchMovies)
        //Then
        val expected = SearchScreenState().copy(isQueryFormatError = true)
        assertThat(viewModel.uiState.value).isEqualTo(expected)
    }

    //good query and then malformed query
    @Test
    fun `given proper first query and second bad query, when search, then isQueryFormatError`() {
        runTest {
            //Given
            var queryValidator = QueryValidatorFake()
            val movieListWithManyItems = listOf<MovieBo>(
                MovieBo(4532, "Marvel: Avangers", 4.1, "imageUrl1"),
                MovieBo(5675, "Marvel: Black Panther", 5.0, "imageUrl2")
            )

            val viewModel = SearchScreenViewModel(
                SearchMoviesUseCaseImplFake(
                    DomainResource.success(movieListWithManyItems)
                ),
                queryValidator,
                backgroundTestDispatcher
            )

            val deliveredState = observeFlow(viewModel.uiState, true) {
                //first search with proper query
                viewModel.onEvent(SearchScreenEvent.OnUpdateQuery("marvel"))
                viewModel.onEvent(SearchScreenEvent.OnSearchMovies)
                //second search with bad query
                queryValidator.isValidQuery = false
                viewModel.onEvent(SearchScreenEvent.OnSearchMovies)
            }

            val state1 = deliveredState[0]
            val state2 = deliveredState[1]
            //Then
            val expectedDeliveredState1 = SearchScreenState().copy(
                isLoading = false,
                isEmpty = false,
                success = movieListWithManyItems.map { it.toMovieVo() },
                isQueryFormatError = false
            )
            val expectedDeliveredState2 = SearchScreenState().copy(
                isLoading = false,
                isEmpty = false,
                success = movieListWithManyItems.map { it.toMovieVo() },
                isQueryFormatError = true
            )

            assertThat(state1).isEqualTo(expectedDeliveredState1)
            assertThat(state2).isEqualTo(expectedDeliveredState2)
        }
    }

    @Test
    fun `given connectivity error after success search, error is of type ConnectivityError and data available`() =
        runTest {
            //Given
            val movieListWithManyItems = listOf<MovieBo>(
                MovieBo(4532, "Marvel: Avangers", 4.1, "imageUrl1"),
                MovieBo(5675, "Marvel: Black Panther", 5.0, "imageUrl2")
            )

            val searchMoviesUeCase = SearchMoviesUseCaseImplFake(
                DomainResource.success(movieListWithManyItems)
            )

            val viewModel = SearchScreenViewModel(
                searchMoviesUeCase,
                QueryValidatorFake(),
                backgroundTestDispatcher
            )

            //When
            val deliveredState = observeFlow(viewModel.uiState, true) {
                //First successful search
                viewModel.onEvent(SearchScreenEvent.OnUpdateQuery("marvel"))
                viewModel.onEvent(SearchScreenEvent.OnSearchMovies)
                //connectivity error happens
                searchMoviesUeCase.setError(DomainError.ConnectivityError)
                viewModel.onEvent(SearchScreenEvent.OnSearchMovies)
            }

            val state1 = deliveredState[0]
            val state2 = deliveredState[1]
            //Then
            assertThat(state1.success).isEqualTo(movieListWithManyItems.map { it.toMovieVo() })
            assertThat(state2.error).isInstanceOf(SearchError.ConnectivityError::class.java)
            assertThat(state2.success).isEqualTo(movieListWithManyItems.map { it.toMovieVo() })
        }

    class QueryValidatorFake : Validator<String> {
        var isValidQuery: Boolean = true
        override fun validate(data: String): Boolean {
            return isValidQuery
        }
    }
}