package com.example.tddmovieapp.presentation.feature.search.SearchScreenState

import com.example.tddmovieapp.presentation.model.MovieVO

data class SearchScreenState(
    val isLoading: Boolean = false,
    val isEmpty: Boolean = false,
    val error: SearchError? = null,
    val success: List<MovieVO>? = null,
    val queryFormatError: QueryFormatError? = null
)

sealed class QueryFormatError {
    object ShortQuery: QueryFormatError()
    object BadQuery: QueryFormatError()
}

sealed class SearchError {
    object ServerError: SearchError()
    object ConnectivityError: SearchError()
    object UnknownError: SearchError()
}

