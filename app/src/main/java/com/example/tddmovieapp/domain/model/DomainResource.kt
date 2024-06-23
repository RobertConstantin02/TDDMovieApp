package com.example.tddmovieapp.domain.model

sealed class DomainResource<out T> {
    data class Success<out T>(val data: T): DomainResource<T>()
    data class Error(val error: DomainError): DomainResource<Nothing>()

    companion object {
        fun <T> success(successData: T): DomainResource.Success<T> =
            DomainResource.Success(successData)

        fun error(serverError: DomainError): DomainResource.Error =
            DomainResource.Error(serverError)
    }
}
