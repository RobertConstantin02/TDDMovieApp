package com.example.tddmovieapp.presentation.feature.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.tddmovieapp.presentation.feature.search.SearchScreenState.SearchScreenState
import com.example.tddmovieapp.presentation.model.MovieVO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SearchScreenViewModel {

    private val _uiState = MutableStateFlow<SearchScreenState>(SearchScreenState())
    val uiState: StateFlow<SearchScreenState> = _uiState.asStateFlow()

    var queryState by mutableStateOf("")
        private set

    fun updateSearchQuery(newValue: String) {
        queryState = newValue
    }

    fun search(input: String) {
        if (input.equals("not existing movie", true)) {
            _uiState.update { state ->
                state.copy(isEmpty = true)
            }
        } else {
            _uiState.update { state ->
                state.copy(isLoading = false, success = listOf<MovieVO>(MovieVO(1, "Iron Man", 4.5, "imageUrl")))
            }
        }
    }
}
