package com.example.tddmovieapp.data.repository

import com.example.tddmovieapp.data.datasource.IMoviesRemoteDataSource
import com.example.tddmovieapp.data.mapper.toMovieBo
import com.example.tddmovieapp.data.model.MovieError
import com.example.tddmovieapp.data.service.MoviesService
import com.example.tddmovieapp.domain.model.DomainError
import com.example.tddmovieapp.domain.model.DomainResource
import com.example.tddmovieapp.domain.model.MovieBo
import com.example.tddmovieapp.domain.repository.IMoviesRepository
import retrofit2.HttpException
import java.io.IOException

//class MoviesRepository(private val moviesDataSource: IMoviesRemoteDataSource) : IMoviesRepository {
//    override suspend fun searchMovies(query: String): DomainResource<List<MovieBo>> =
//        try {
//            val result = moviesDataSource.getMovies(query).map { it.toMovieBo() }
//            if (result.isNotEmpty()) DomainResource.success(result)
//            else DomainResource.success(emptyList())
//        } catch (e: Exception) {
//            when (e) {
//                is MovieError.ServerError -> DomainResource.error(DomainError.ServerError)
//                is MovieError.ConnectivityError -> DomainResource.error(DomainError.ConnectivityError)
//                else -> DomainResource.error(DomainError.Unknown)
//            }
//        }
//}

class MoviesRepository(private val api: MoviesService) : IMoviesRepository {
    override suspend fun searchMovies(query: String): DomainResource<List<MovieBo>> =
        try {
            val result = api.getMovies(query)
            if (result.isSuccessful && result.body() != null)
                DomainResource.success(result.body()!!.results.map { it.toMovieBo() })
            else DomainResource.success(emptyList())
        } catch (e: Throwable) {
            when (e) {
                is IOException -> DomainResource.error(DomainError.ConnectivityError)
                is HttpException -> DomainResource.error(DomainError.ServerError)
                else -> DomainResource.error(DomainError.Unknown)
            }
        }
}