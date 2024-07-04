package com.example.tddmovieapp.data.repository

import com.example.tddmovieapp.data.test_doubles.MoviesRepositoryFake
import com.example.tddmovieapp.domain.model.DomainError
import com.example.tddmovieapp.domain.model.MovieBo
import com.example.tddmovieapp.domain.repository.IMoviesRepository

/**
 * Here we took the fake and we wrote test for it. And from those test we extract a contract
 *
 * After extracting the contract test we are left here with only the functions. What they do is
 * only supply an instance of the repository that will be needed in each case.
 *
 */
class MoviesRepositoryFakeTestTest : MoviesRepositoryContractTest() {

    override val movieList: List<MovieBo>
        get() = listOf(
            MovieBo(4532, "Marvel: Avangers", 4.1, "imageUrl1"),
            MovieBo(5675, "Marvel: Black Panther", 5.0, "imageUrl2"),
        )

    override fun searchMoviesWithServiceError(): IMoviesRepository =
        MoviesRepositoryFake(null, DomainError.ServerError)

    override fun searchMoviesWithConnectivityError(): IMoviesRepository =
        MoviesRepositoryFake(null, DomainError.ConnectivityError)

    override fun searchMoviesSuccess(movieList: List<MovieBo>): IMoviesRepository =
        MoviesRepositoryFake(movieList)
}