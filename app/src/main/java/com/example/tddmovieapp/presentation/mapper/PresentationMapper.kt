package com.example.tddmovieapp.presentation.mapper

import com.example.tddmovieapp.domain.model.DomainSearchError
import com.example.tddmovieapp.presentation.feature.search.SearchError

fun DomainSearchError.toErrorVo(): SearchError =
    when(this) {
        is DomainSearchError.ServerError -> SearchError.ServerError
        is DomainSearchError.ConnectivityError -> SearchError.ConnectivityError
    }
