package com.example.tddmovieapp.presentation.mapper

import com.example.tddmovieapp.domain.model.DomainSearchError
import com.example.tddmovieapp.presentation.feature.search.SearchError

fun DomainSearchError.toErrorVo(): SearchError = SearchError.ServerError
