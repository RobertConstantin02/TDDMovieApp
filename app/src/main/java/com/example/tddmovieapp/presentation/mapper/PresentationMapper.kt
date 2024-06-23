package com.example.tddmovieapp.presentation.mapper

import com.example.tddmovieapp.domain.model.DomainError
import com.example.tddmovieapp.domain.model.MovieBo
import com.example.tddmovieapp.presentation.feature.search.SearchError
import com.example.tddmovieapp.presentation.model.MovieVO

fun DomainError.toErrorVo(): SearchError =
    when(this) {
        is DomainError.ServerError -> SearchError.ServerError
        is DomainError.ConnectivityError -> SearchError.ConnectivityError
    }

fun MovieBo.toMovieVo() = MovieVO(id, title, popularity, image)