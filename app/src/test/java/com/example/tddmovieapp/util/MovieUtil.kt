package com.example.tddmovieapp.util

import com.example.tddmovieapp.data.model.MovieSearchDto
import com.example.tddmovieapp.util.GsonAdapterExt.fromJson

const val SUCCESS_MOVIES_FIRST_PAGE_JSON = "json/successGetMovies.json"
const val EMPTY_MOVIES_JSON = "json/emptyGetMovies.json"

object MovieUtil {
    val expectedSuccessMovies = FileUtil.getJson(SUCCESS_MOVIES_FIRST_PAGE_JSON)
        ?.fromJson<MovieSearchDto>() as MovieSearchDto

    val expectedEmptyMovies = FileUtil.getJson(EMPTY_MOVIES_JSON)
        ?.fromJson<MovieSearchDto>() as MovieSearchDto
}

object FileUtil {
    fun getJson(file: String) =
        javaClass.classLoader?.getResource(file)?.readText()
}
