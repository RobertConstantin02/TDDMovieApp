package com.example.tddmovieapp.presentation.feature.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tddmovieapp.domain.usecase.SearchMoviesUseCase
import com.example.tddmovieapp.presentation.di.DispatcherIO
import com.example.tddmovieapp.presentation.di.SearchQueryValidator
import com.example.tddmovieapp.presentation.feature.util.Validator
import com.example.tddmovieapp.presentation.mapper.toErrorVo
import com.example.tddmovieapp.presentation.mapper.toMovieVo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SearchScreenViewModel @Inject constructor(
    private val searchMovies: SearchMoviesUseCase,
    @SearchQueryValidator private val queryValidator: Validator<String>,
    @DispatcherIO private val backgroundDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchScreenState())
    val uiState: StateFlow<SearchScreenState> = _uiState.asStateFlow()

    var queryState by mutableStateOf("")
        private set

    fun onEvent(event: SearchScreenEvent) {
        when (event) {
            is SearchScreenEvent.OnUpdateQuery -> {
                queryState = event.input
                if (_uiState.value.isQueryFormatError) {
                    _uiState.update { state ->
                        state.copy(isQueryFormatError = false)
                    }
                }
            }
            is SearchScreenEvent.OnSearchMovies -> search(queryState)
        }
    }

    private fun search(input: String) {
        _uiState.update { state ->
            state.copy(isLoading = true)
        }
        if (queryValidator.validate(queryState)) {
            viewModelScope.launch {
                withContext(backgroundDispatcher) {
                    searchMovies.invoke(
                        input,
                        success = { searchedMovies ->
                            _uiState.update { state ->
                                state.copy(
                                    isLoading = false,
                                    isEmpty = false,
                                    success = searchedMovies.map { it.toMovieVo() })
                            }
                        },
                        empty = {
                            _uiState.update { state ->
                                state.copy(isLoading = false, isEmpty = true)
                            }
                        },
                        error = { error ->
                            _uiState.update { state ->
                                state.copy(
                                    isLoading = false,
                                    isEmpty = true,
                                    error = error.toErrorVo()
                                )
                            }
                        }
                    )
                }
            }
        } else {
            _uiState.update { state ->
                state.copy(isQueryFormatError = true, isLoading = false)
            }
        }
    }
}




