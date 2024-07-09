package com.example.tddmovieapp.data.service

import com.example.tddmovieapp.data.model.MovieSearchDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

const val BASE_API_URL = "https://api.themoviedb.org/"
interface MoviesService {
    @GET("3/search/movie?api_key=f5f8c4583a010bfe23670dff2fe0442d")
    suspend fun getMovies(
        @Query("query") query: String,
        @Query("include_adult") adult: Boolean = false
    ): Response<MovieSearchDto>
}