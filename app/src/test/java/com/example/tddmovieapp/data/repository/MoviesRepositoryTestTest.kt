package com.example.tddmovieapp.data.repository

import com.example.tddmovieapp.domain.model.DomainError
import com.example.tddmovieapp.domain.model.DomainResource
import com.example.tddmovieapp.domain.model.MovieBo
import com.example.tddmovieapp.domain.repository.IMoviesRepository

class MoviesRepositoryTestTest: MoviesRepositoryContractTest() {
    override fun searchMoviesWithError(error: DomainError): IMoviesRepository {

    }

    override fun searchMoviesSuccess(movieList: List<MovieBo>): IMoviesRepository {
        TODO("Not yet implemented")
    }
}

class MoviesRepository(private val IMoviesDataSource): IMoviesRepository {
    override suspend fun searchMovies(query: String): DomainResource<List<MovieBo>> {

    }
}
