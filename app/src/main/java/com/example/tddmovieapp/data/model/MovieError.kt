package com.example.tddmovieapp.data.model

sealed class MovieError: Exception() {
    data object ServerError: MovieError()
    data object ConnectivityError: MovieError()
    data object Unknown: MovieError()
}