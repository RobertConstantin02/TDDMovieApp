package com.example.tddmovieapp.presentation.mapper

import com.example.tddmovieapp.domain.model.DomainError
import com.example.tddmovieapp.presentation.feature.search.SearchError

fun DomainError.toErrorVo(): SearchError =
    when(this) {
        is DomainError.ServerError -> SearchError.ServerError
        is DomainError.ConnectivityError -> SearchError.ConnectivityError
    }
