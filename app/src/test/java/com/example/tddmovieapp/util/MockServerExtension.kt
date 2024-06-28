package com.example.tddmovieapp.util

import com.example.tddmovieapp.data.di.NetworkModule.provideInterceptor
import com.example.tddmovieapp.data.di.NetworkModule.provideMovieService
import com.example.tddmovieapp.data.di.NetworkModule.provideOkHttpClient
import com.example.tddmovieapp.data.di.NetworkModule.provideService
import com.example.tddmovieapp.data.service.MoviesService
import okhttp3.mockwebserver.MockWebServer

fun MockWebServer.toMoviesService(): MoviesService {
    val retrofit = provideService(
        baseUrl = url("").toString(),
        client = provideOkHttpClient(interceptor = provideInterceptor())
    )
    return provideMovieService(retrofit)
}