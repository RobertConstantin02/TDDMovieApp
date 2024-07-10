package com.example.tddmovieapp.domain.test_doubles

import com.example.tddmovieapp.data.mapper.toMovieBo
import com.example.tddmovieapp.data.model.MovieSearchDto
import com.example.tddmovieapp.domain.model.DomainError
import com.example.tddmovieapp.domain.model.DomainResource
import com.example.tddmovieapp.domain.model.MovieBo
import com.example.tddmovieapp.domain.repository.IMoviesRepository

/**
 * This repository fake logic inside was extracted while I was working in the business logic in the use case
 * So as i was going I was figured the logic and I end up with this logic unit.
 * Here we have the list of movies which is basically a list and this is wrapped into some object that exposes
 * an api searchMovies that provides domain type DomainResource.
 *
 * This class is already covered with test because when we were testing the use case, we came up with that
 * logic. So if we are changing something here the test will catch the error because this instance of
 * that class is already used in the test of the use case.
 */
class MoviesRepositoryFake(
    private var listMovies: List<MovieBo>?,
    private var error: DomainError? = null
) : IMoviesRepository {
    override suspend fun searchMovies(query: String): DomainResource<List<MovieBo>> {
        error?.let { return DomainResource.error(it) }
        val filteredMovies = listMovies?.filter { movie -> movie.title?.contains(query, true) == true }
        return if (filteredMovies?.isNotEmpty() == true) DomainResource.success(filteredMovies)
        else DomainResource.success(emptyList())
    }
}