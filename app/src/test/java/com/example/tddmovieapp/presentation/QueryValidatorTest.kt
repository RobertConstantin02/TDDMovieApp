package com.example.tddmovieapp.presentation

import com.example.tddmovieapp.presentation.feature.util.QueryValidator
import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class QueryValidatorTest {

    @ParameterizedTest
    @CsvSource(
        "'iron man', true",
        "'iro', false",
        "'    ', false",
        "'   iron ma', true",
        "'', false",
        "'iron-man', false",
        "'* iron', false"
    )
    fun `given a proper query, when search, then validator returns true`(query: String, expected: Boolean) {
        //Given
        val validator = QueryValidator()
        //When
        val validatorResult = validator.validate(query)
        //Then
        assertThat(validatorResult).isEqualTo(expected)
    }
}