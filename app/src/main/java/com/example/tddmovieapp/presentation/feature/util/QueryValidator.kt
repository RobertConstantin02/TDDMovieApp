package com.example.tddmovieapp.presentation.feature.util

import java.util.regex.Pattern

class QueryValidator {
    fun validate(query: String): Boolean {
        return !(query.trim().length <= 3 || containsSpecialCharacters(query))
    }

    private fun containsSpecialCharacters(query: String): Boolean {
        val regexSpecialCharacters = "[<({\\-=!|$})?.*+\\[\\]>/]"
        val pattern = Pattern.compile(regexSpecialCharacters)
        return pattern.matcher(query).find()
    }
}
