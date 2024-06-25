package com.example.tddmovieapp.data.mapper

import com.example.tddmovieapp.data.model.MovieSearchDto
import com.example.tddmovieapp.domain.model.MovieBo

fun MovieSearchDto.MovieDto.toMovieBo(): MovieBo =
    MovieBo(id, title, popularity, image)