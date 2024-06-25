package com.example.tddmovieapp.data.mapper

import com.example.tddmovieapp.data.model.MovieSearchDto
import com.example.tddmovieapp.domain.model.MovieBo
import org.junit.jupiter.api.Test
import com.google.common.truth.Truth.assertThat

class MoviesDtoMapperTest {

    @Test
    fun `data model movieDto,is mapped to business logic model, movieBo`() {

        //Given
        val expected = MovieBo(1, "Marvel: Iron Man", 4.32, "image/image.jpg")
        val movieDto = MovieSearchDto.MovieDto(1, "Marvel: Iron Man", 4.32, "image/image.jpg")
        //When
        val result = movieDto.toMovieBo()
        //Then
        assertThat(result).isEqualTo(expected)
    }
}
