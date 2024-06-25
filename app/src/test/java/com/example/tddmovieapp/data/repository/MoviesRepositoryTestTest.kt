package com.example.tddmovieapp.data.repository

import com.example.tddmovieapp.data.model.MovieError
import com.example.tddmovieapp.data.model.MovieSearchDto
import com.example.tddmovieapp.data.test_doubles.MovieRemoteDatasourceFake
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

//For now has only sense here in this test
fun MovieBo.toMovieDto() = MovieSearchDto.MovieDto(id, title, popularity, image)
