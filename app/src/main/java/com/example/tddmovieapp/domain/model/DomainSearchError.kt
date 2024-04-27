package com.example.tddmovieapp.domain.model

sealed class DomainSearchError {
    object ServerError: DomainSearchError()
    object ConnectivityError: DomainSearchError()
}
