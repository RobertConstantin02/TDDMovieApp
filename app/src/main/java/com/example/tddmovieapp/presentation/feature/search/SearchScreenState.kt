package com.example.tddmovieapp.presentation.feature.search

import com.example.tddmovieapp.presentation.model.MovieVO

data class SearchScreenState(
    val isLoading: Boolean = false,
    val isEmpty: Boolean = false,
    val error: SearchError? = null,
    val success: List<MovieVO>? = null,
    val isQueryFormatError: Boolean = false
)

sealed class SearchError {
    object ServerError: SearchError()
    object ConnectivityError: SearchError()
    object UnknownError: SearchError()
}

