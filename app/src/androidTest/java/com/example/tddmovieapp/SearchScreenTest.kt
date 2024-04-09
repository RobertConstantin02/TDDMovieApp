package com.example.tddmovieapp

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.example.tddmovieapp.robot.launchSearchScreen
import org.junit.Rule
import org.junit.Test

class SearchScreenTest {

    @get:Rule
    val rule = createAndroidComposeRule<MainActivity>()

    @Test
    fun performSearchMovies() {
        launchSearchScreen(rule) {
            typeMovieTitle()
            search()
        } verify {
            searchedMoviesAreShown()
        }
    }

    private fun typeMovieTitle() {
        val searchInput = "iron man"
        val searchField = rule.activity.getString(R.string.search_field_hint)
        rule.onNodeWithContentDescription(searchField).performTextInput(searchInput)
    }

    private fun search() {
        val searchButton = rule.activity.getString(R.string.search_button)
        rule.onNodeWithContentDescription(searchButton).performClick()
    }
}