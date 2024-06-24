package com.example.tddmovieapp.data.repository

import com.example.tddmovieapp.domain.model.DomainError
import com.example.tddmovieapp.domain.model.DomainResource
import com.example.tddmovieapp.domain.model.MovieBo
import com.example.tddmovieapp.domain.repository.IMoviesRepository
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

/**
 * This contract test encapsulates the data, test and abstract functions.
 */
abstract class MoviesRepositoryContractTest {

    private val movieList = listOf(
        MovieBo(4532, "Marvel: Avangers", 4.1, "imageUrl1"),
        MovieBo(5675, "Marvel: Black Panther", 5.0, "imageUrl2"),
    )

    @Test
    fun `repository returns success with movies containing query`() = runTest {
        //Given
        val query = "marvel"
        val expected = DomainResource.success(movieList)
        val repository = searchMoviesSuccess(movieList) //here change for searchMoviesWith
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
    fun `repository returns error`() = runTest {
        //Given
        val query = "marvel"
        val expected = DomainResource.error(DomainError.ServerError)
        val repository = searchMoviesWithError(DomainError.ServerError)
        //When
        val result = repository.searchMovies(query)
        //Then
        assertThat(result).isInstanceOf(expected::class.java)
        assertThat((result as DomainResource.Error).error).isInstanceOf(DomainError.ServerError::class.java)
    }

    abstract fun searchMoviesWithError(error: DomainError): IMoviesRepository

    abstract fun searchMoviesSuccess(movieList: List<MovieBo>): IMoviesRepository
}