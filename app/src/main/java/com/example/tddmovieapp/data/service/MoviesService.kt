package com.example.tddmovieapp.data.service

import retrofit2.http.GET
import retrofit2.http.Query

const val BASE_API_URL = "https://api.themoviedb.org"
interface MoviesService {
    @GET("3/search/movie")
    fun getMovies(
        @Query("query") query: String,
        @Query("include_adult") adult: Boolean = false
    )
}