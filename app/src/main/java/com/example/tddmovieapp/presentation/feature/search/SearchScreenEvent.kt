package com.example.tddmovieapp.presentation.feature.search

sealed class SearchScreenEvent {
    data class OnUpdateQuery(val input: String): SearchScreenEvent()
    object OnSearchMovies: SearchScreenEvent()
}
