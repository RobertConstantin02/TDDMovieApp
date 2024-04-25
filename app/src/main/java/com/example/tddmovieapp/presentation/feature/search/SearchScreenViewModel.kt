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

    val movies = listOf(
        MovieVO(131, "Iron Man", 4.5, "imageUrl"),
        MovieVO(4532, "Marvel: Avangers", 4.1, "imageUrl1"),
        MovieVO(5675, "Marvel: Black Panther", 5.0, "imageUrl2")
    )

    fun search(input: String) {
        val searchedMovies = movies.filter { movie ->
            movie.title.contains(input, true)
        }

        if (searchedMovies.isNotEmpty()) {
            _uiState.update { state ->
                state.copy(isLoading = false, isEmpty = false, success = searchedMovies)
            }
        } else {
            _uiState.update { state ->
                state.copy(isLoading = false, isEmpty = true)
            }
        }

//        if (input.equals("not existing movie", true)) {
//            _uiState.update { state ->
//                state.copy(isEmpty = true)
//            }
//        } else if (input.equals("iron", true)) {
//            _uiState.update { state ->
//                state.copy(
//                    isLoading = false,
//                    isEmpty = false,
//                    success = listOf<MovieVO>(MovieVO(131, "Iron Man", 4.5, "imageUrl"))
//                )
//            }
//        } else {
//            _uiState.update { state ->
//                state.copy(
//                    isLoading = false, isEmpty = false, success = listOf<MovieVO>(
//                        MovieVO(4532, "Avangers", 4.1, "imageUrl1"),
//                        MovieVO(5675, "Black Panther", 5.0, "imageUrl2")
//                    )
//                )
//            }
//        }
    }
}
