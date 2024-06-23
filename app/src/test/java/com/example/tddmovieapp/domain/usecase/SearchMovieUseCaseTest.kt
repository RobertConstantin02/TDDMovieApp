package com.example.tddmovieapp.domain.usecase

import com.example.tddmovieapp.data.test_doubles.MoviesRepositoryFake
import com.example.tddmovieapp.domain.model.DomainError
import com.example.tddmovieapp.domain.model.MovieBo
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class SearchMovieUseCaseTest {

    private var isSuccessCalled: Boolean = false
    private var isEmptyCalled: Boolean = false
    private var isErrorCalled: Boolean = false

    private val movieList = listOf(
        MovieBo(4532, "Marvel: Avangers", 4.1, "imageUrl1"),
        MovieBo(5675, "Marvel: Black Panther", 5.0, "imageUrl2"),
    )

    private lateinit var successCallback: (listMovies: List<MovieBo>) -> Unit
    private lateinit var emptyCallback: () -> Unit
    private lateinit var errorCallBack: (DomainError) -> Unit

    @BeforeEach
    fun setUp() {
        successCallback = { isSuccessCalled = true }
        emptyCallback =  { isEmptyCalled = true }
        errorCallBack =  { isErrorCalled = true }
    }

    @Test
    fun `when list of movies is empty, then empty lambda is called`() = runTest {
        //Given
        val validQuery = "not exists in db"
        //When
        SearchMoviesUseCaseImpl(MoviesRepositoryFake(movieList)).invoke(
            validQuery,
            successCallback,
            emptyCallback,
            errorCallBack
        )
        //Then
        assertThat(isEmptyCalled).isTrue()
    }

    @Test
    fun `when list of movies is success, then success lambda is called`() = runTest {
        //Given
        val validQuery = "marvel"

        //When
        SearchMoviesUseCaseImpl(MoviesRepositoryFake(movieList)).invoke(
            validQuery,
            successCallback,
            emptyCallback,
            errorCallBack
        )
        //Then
        assertThat(isSuccessCalled).isTrue()
    }

    @Test
    fun `when connectivity error, then error lambda is called`() = runTest {
        //Given
        val validQuery = "marvel"
        //When
        SearchMoviesUseCaseImpl(MoviesRepositoryFake(movieList, DomainError.ConnectivityError)).invoke(
            validQuery,
            successCallback,
            emptyCallback,
            errorCallBack
        )
        //Then
        assertThat(isErrorCalled).isTrue()
    }
}