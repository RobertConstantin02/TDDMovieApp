package com.example.tddmovieapp.presentation.feature.util

import java.util.regex.Pattern

class QueryValidator {
    fun validate(query: String): Boolean {
        return if (query.trim().length <= 3 || containsSpecialCharacters(query)) false
        else true
    }

    private fun containsSpecialCharacters(query: String): Boolean {
        val regexSpecialCharacters = "[<({\\-=!|$})?.*+\\[\\]>/]"
        val pattern = Pattern.compile(regexSpecialCharacters)
        return pattern.matcher(query).find()
    }
}
