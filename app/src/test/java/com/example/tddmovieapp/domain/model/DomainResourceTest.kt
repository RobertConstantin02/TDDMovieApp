package com.example.tddmovieapp.domain.model

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test

class DomainResourceTest {

    @Test
    fun `Given list of movies, when calling success function, then return DomainResource with that data`() {
        //Given
        val expected = listOf(
            MovieBo(4532, "Marvel: Avangers", 4.1, "imageUrl1"),
            MovieBo(5675, "Marvel: Black Panther", 5.0, "imageUrl2")
        )
        //When
        val domainResult = DomainResource.success(expected)
        //Then
        assertThat(domainResult.data).isEqualTo(expected)
    }

    @Test
    fun `When calling error function with server error, then return DomainResource with that error`() {
        val expected = DomainError.ServerError
        //Whena
        val domainResult = DomainResource.error(DomainError.ServerError)
        //Then
        assertThat(domainResult.error).isEqualTo(expected)
    }
}