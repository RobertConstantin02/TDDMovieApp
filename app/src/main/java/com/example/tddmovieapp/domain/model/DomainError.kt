package com.example.tddmovieapp.domain.model

sealed class DomainError {
    object ServerError: DomainError()
    object ConnectivityError: DomainError()
    object Unknown: DomainError()
}
