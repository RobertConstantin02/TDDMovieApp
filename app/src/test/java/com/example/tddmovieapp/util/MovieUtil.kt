package com.example.tddmovieapp.util

import com.example.tddmovieapp.data.model.MovieSearchDto
import com.example.tddmovieapp.util.GsonAdapterExt.fromJson

const val MOVIES_FIRST_PAGE_JSON = "json/getMovies.json"

object MovieUtil {
    val expectedSuccessMovies = FileUtil.getJson(MOVIES_FIRST_PAGE_JSON)
        ?.fromJson<MovieSearchDto>() as MovieSearchDto
}

object FileUtil {
    fun getJson(file: String) =
        javaClass.classLoader?.getResource(file)?.readText()
}
