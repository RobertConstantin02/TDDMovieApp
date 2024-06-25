package com.example.tddmovieapp.data.datasource

import com.example.tddmovieapp.data.model.MovieSearchDto
import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

abstract class MoviesRemoteDataSourceContractTest {
    abstract val expectedMovieList: List<MovieSearchDto.MovieDto>
    @Test
    fun `remote datasource return success movie list`() {
        //Given
        val remoteDataSource = successMovies()
        //Then
        val result = remoteDataSource.getMovies()
        //Then
        assertThat(result).isEqualTo(expectedMovieList)
    }

    @Test
    fun `remote data source return success empty movie list`() {
        val remoteDataSource = successMoviesEmpty()
        //Then
        val result = remoteDataSource.getMovies()
        //Then
        assertThat(result).isEmpty()
    }

    @Test
    fun `remote data source throws exception`() {
        //Given
        val remoteDataSource = errorMovies()
        //Then
        assertThrows<Throwable> {
            remoteDataSource.getMovies()
        }
    }

    abstract fun successMovies(): IMoviesRemoteDataSource

    abstract fun successMoviesEmpty(): IMoviesRemoteDataSource

    abstract fun errorMovies(): IMoviesRemoteDataSource
}