package com.example.tddmovieapp.presentation.feature.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.tddmovieapp.domain.usecase.ISearchMoviesUseCase
import com.example.tddmovieapp.presentation.feature.search.test_doubles.SearchMoviesUseCaseStub
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SearchScreenViewModel(private val searchMoviesUseCaseStub: ISearchMoviesUseCase) {

    private val _uiState = MutableStateFlow(SearchScreenState())
    val uiState: StateFlow<SearchScreenState> = _uiState.asStateFlow()

    var queryState by mutableStateOf("")
        private set

    fun updateSearchQuery(newValue: String) {
        queryState = newValue
    }

    fun search(input: String) {
        searchMoviesUseCaseStub.invoke(
            input,
            success = { searchedMovies ->
                _uiState.update { state ->
                    state.copy(isLoading = false, isEmpty = false, success = searchedMovies)
                }
            },
            empty = {
                _uiState.update { state ->
                    state.copy(isLoading = false, isEmpty = true)
                }
            }
        )
    }
}
