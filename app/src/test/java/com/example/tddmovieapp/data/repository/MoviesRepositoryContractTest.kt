package com.example.tddmovieapp.data.repository

import com.example.tddmovieapp.data.mapper.toMovieBo
import com.example.tddmovieapp.data.model.MovieSearchDto
import com.example.tddmovieapp.domain.model.DomainError
import com.example.tddmovieapp.domain.model.DomainResource
import com.example.tddmovieapp.domain.model.MovieBo
import com.example.tddmovieapp.domain.repository.IMoviesRepository
import com.example.tddmovieapp.util.CoroutineExtension
import com.example.tddmovieapp.util.MovieUtil
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

/**
 * This contract test encapsulates the data, test and abstract functions.
 */
@ExtendWith(CoroutineExtension::class)
abstract class MoviesRepositoryContractTest {

    private val query: String = "marvel"

    private val successMoviesDto: List<MovieSearchDto.MovieDto> = MovieUtil.expectedSuccessMovies.results
    private val successMoviesDomain = successMoviesDto.map { it.toMovieBo() }

    private val successEmptyMoviesDto: List<MovieSearchDto.MovieDto> = MovieUtil.expectedEmptyMovies.results


    @Test
    fun `repository returns success with movies containing query`() = runTest {
        //Given
        val expected = DomainResource.success(successMoviesDomain)
        val repository = searchMoviesSuccess(successMoviesDto)
        //When
        val result = repository.searchMovies(query)
        //Then
        assertThat(result).isInstanceOf(expected::class.java)
        assertThat((result as DomainResource.Success<List<MovieBo>>).data).isEqualTo(expected.data)
    }

    @Test
    fun `repository returns success with empty movies`() = runTest {
        //Given
        val expected = DomainResource.success(emptyList<MovieBo>())
        val repository = searchMoviesSuccess(successEmptyMoviesDto)
        //When
        val result = repository.searchMovies(query)
        //Then
        assertThat(result).isInstanceOf(expected::class.java)
        assertThat((result as DomainResource.Success<List<MovieBo>>).data).isEmpty()
    }

    @Test
    fun `repository returns empty data`() = runTest {
        //Given
        val expected = DomainResource.success(listOf<MovieBo>())
        val repository = searchMoviesSuccess(successMoviesDto)
        //When
        val result = repository.searchMovies(query)
        //Then
        assertThat(result).isInstanceOf(expected::class.java)
    }

    @Test
    fun `repository returns service error`() = runTest {
        //Given
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

    abstract fun searchMoviesSuccess(movieList: List<MovieSearchDto.MovieDto>): IMoviesRepository
}