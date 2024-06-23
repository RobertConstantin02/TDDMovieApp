package com.example.tddmovieapp.presentation.feature.util

interface Validator<T> {
    fun validate(data: T): Boolean
}