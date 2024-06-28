package com.example.tddmovieapp.data.service

import com.example.tddmovieapp.data.model.MovieSearchDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

const val BASE_API_URL = "https://api.themoviedb.org/3"
interface MoviesService {
    @GET("search/movie")
    suspend fun getMovies(
        @Query("query") query: String,
        @Query("include_adult") adult: Boolean = false
    ): Response<MovieSearchDto>
}