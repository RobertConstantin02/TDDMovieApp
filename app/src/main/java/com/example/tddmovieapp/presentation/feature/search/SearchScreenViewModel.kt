package com.example.tddmovieapp.presentation.feature.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.tddmovieapp.domain.usecase.SearchMoviesUseCase
import com.example.tddmovieapp.presentation.feature.util.QueryValidator
import com.example.tddmovieapp.presentation.feature.util.Validator
import com.example.tddmovieapp.presentation.mapper.toErrorVo
import com.example.tddmovieapp.presentation.mapper.toMovieVo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SearchScreenViewModel(
    private val searchMoviesUseCaseStub: SearchMoviesUseCase,
    private val queryValidator: Validator<String>
) {

    private val _uiState = MutableStateFlow(SearchScreenState())
    val uiState: StateFlow<SearchScreenState> = _uiState.asStateFlow()

    var queryState by mutableStateOf("")
        private set

    fun onEvent(event: SearchScreenEvent) {
        when (event) {
            is SearchScreenEvent.OnUpdateQuery -> queryState = event.input
            is SearchScreenEvent.OnSearchMovies -> search(queryState)
        }
    }

    private fun search(input: String) {
        if (queryValidator.validate(queryState)) {
            searchMoviesUseCaseStub.invoke(
                input,
                success = { searchedMovies ->
                    _uiState.update { state ->
                        state.copy(isLoading = false, isEmpty = false, success = searchedMovies.map { it.toMovieVo() })
                    }
                },
                empty = {
                    _uiState.update { state ->
                        state.copy(isLoading = false, isEmpty = true)
                    }
                },
                error = { error ->
                    _uiState.update { state ->
                        state.copy(isLoading = false, isEmpty = true, error = error.toErrorVo())
                    }
                }
            )
        } else {
            _uiState.update { state ->
                state.copy(isQueryFormatError = true)
            }
        }
    }
}




