package com.example.tddmovieapp.data.datasource

import com.example.tddmovieapp.data.model.MovieSearchDto
import com.example.tddmovieapp.util.MovieUtil
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

abstract class MoviesRemoteDataSourceContractTest {
    //here we could have more types. like without result etc...
    abstract val expectedMovieList: List<MovieSearchDto.MovieDto>
    abstract val query: String
    @Test
    fun `remote datasource return success movie list`() = runTest {
        //Given
        val remoteDataSource = successMovies()
        //Then
        val result = remoteDataSource.getMovies(query)
        //Then
        assertThat(result).isEqualTo(expectedMovieList)
    }

    @Test
    fun `remote data source return success empty movie list`() = runTest {
        //Given
        val remoteDataSource = successMoviesEmpty()
        //When
        val result = remoteDataSource.getMovies(query)
        //Then
        assertThat(result).isEmpty()
    }

    @Test
    fun `remote data source throws exception`() = runTest {
        //Given
        val remoteDataSource = errorMovies()
        //Then
        assertThrows<Throwable> {
            remoteDataSource.getMovies(query)
        }
    }

    abstract fun successMovies(): IMoviesRemoteDataSource

    abstract fun successMoviesEmpty(): IMoviesRemoteDataSource

    abstract fun errorMovies(): IMoviesRemoteDataSource
}