package com.example.tddmovieapp.data.repository

import com.example.tddmovieapp.data.datasource.IMoviesRemoteDataSource
import com.example.tddmovieapp.data.mapper.toMovieBo
import com.example.tddmovieapp.data.model.MovieError
import com.example.tddmovieapp.data.model.MovieSearchDto
import com.example.tddmovieapp.data.test_doubles.MovieRemoteDatasourceFake
import com.example.tddmovieapp.domain.model.DomainError
import com.example.tddmovieapp.domain.model.DomainResource
import com.example.tddmovieapp.domain.model.MovieBo
import com.example.tddmovieapp.domain.repository.IMoviesRepository

class MoviesRepositoryTestTest : MoviesRepositoryContractTest() {
    override fun searchMoviesWithError(): IMoviesRepository {
        val remoteDatasource = MovieRemoteDatasourceFake(null, MovieError.ServerError)
        return MoviesRepository(remoteDatasource)
    }

    override fun searchMoviesSuccess(movieList: List<MovieBo>): IMoviesRepository {
        val remoteDataSource = MovieRemoteDatasourceFake(movieList.map { it.toMovieDto() }, null)
        return MoviesRepository(remoteDataSource)
    }
}

class MoviesRepository(private val moviesDataSource: IMoviesRemoteDataSource) : IMoviesRepository {
    override suspend fun searchMovies(query: String): DomainResource<List<MovieBo>> =
        try {
            val result = moviesDataSource.getMovies().map { it.toMovieBo() }
            if (result.isNotEmpty()) DomainResource.success(result)
            else DomainResource.success(emptyList())
        } catch (e: Exception) {
            when (e) {
                is MovieError.ServerError -> DomainResource.error(DomainError.ServerError)
                is MovieError.ConnectivityError -> DomainResource.error(DomainError.ConnectivityError)
                else -> DomainResource.error(DomainError.Unknown)
            }
        }
}

//For now has only sense here in this test
fun MovieBo.toMovieDto() = MovieSearchDto.MovieDto(id, title, popularity, image)
