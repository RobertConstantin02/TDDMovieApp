package com.example.tddmovieapp.data.repository

import com.example.tddmovieapp.data.model.MovieSearchDto
import com.example.tddmovieapp.domain.model.DomainError
import com.example.tddmovieapp.domain.model.DomainResource
import com.example.tddmovieapp.domain.model.MovieBo
import com.example.tddmovieapp.domain.repository.IMoviesRepository
import com.example.tddmovieapp.util.CoroutineExtension
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

/**
 * This contract test encapsulates the data, test and abstract functions.
 */
@ExtendWith(CoroutineExtension::class)
abstract class MoviesRepositoryContractTest {

    abstract val movieList: List<MovieBo>

    @Test
    fun `repository returns success with movies containing query`() = runTest {
        //Given
        val query = "marvel"
        val expected = DomainResource.success(movieList)
        val repository = searchMoviesSuccess(movieList)
        //When
        val result = repository.searchMovies(query)
        //Then
        assertThat(result).isInstanceOf(expected::class.java)
        assertThat((result as DomainResource.Success<List<MovieBo>>).data).isEqualTo(expected.data)
    }

    @Test
    fun `repository returns empty data`() = runTest {

        //Given
        val query = "no movie"
        val expected = DomainResource.success(listOf<MovieBo>())
        val repository = searchMoviesSuccess(movieList)
        //When
        val result = repository.searchMovies(query)
        //Then
        assertThat(result).isInstanceOf(expected::class.java)
    }

    @Test
    fun `repository returns service error`() = runTest {
        //Given
        val query = "marvel"
        val expected = DomainResource.error(DomainError.ServerError)
        val repository = searchMoviesWithServiceError()
        //When
        val result = repository.searchMovies(query)
        //Then
        assertThat(result).isInstanceOf(expected::class.java)
        assertThat((result as DomainResource.Error).error).isInstanceOf(DomainError.ServerError::class.java)
    }

    @Test
    fun `repository returns connectivity  error`() = runTest {
        //Given
        val query = "marvel"
        val expected = DomainResource.error(DomainError.ConnectivityError)
        val repository = searchMoviesWithConnectivityError()
        //When
        val result = repository.searchMovies(query)
        //Then
        assertThat(result).isInstanceOf(expected::class.java)
        assertThat((result as DomainResource.Error).error).isInstanceOf(DomainError.ConnectivityError::class.java)
    }

    abstract fun searchMoviesWithServiceError(): IMoviesRepository
    abstract fun searchMoviesWithConnectivityError(): IMoviesRepository

    abstract fun searchMoviesSuccess(movieList: List<MovieBo>): IMoviesRepository
}