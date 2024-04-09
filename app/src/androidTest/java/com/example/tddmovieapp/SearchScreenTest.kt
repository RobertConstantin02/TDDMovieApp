package com.example.tddmovieapp

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.example.tddmovieapp.presentation.model.MovieVO
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

fun launchSearchScreen(
    rule: AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>,
    block: SearchScreenRobot.() -> Unit
): SearchScreenRobot {
    return SearchScreenRobot(rule).apply(block)
}

class SearchScreenRobot(private val rule: AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>) {
    infix fun verify(block: SearchScreenVerification.() -> Unit): SearchScreenVerification {
        return SearchScreenVerification(rule).apply(block)
    }
}

class SearchScreenVerification(private val rule: AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>) {
    fun searchedMoviesAreShown(vararg movies: MovieVO) {
        repeat(movies.count()) {
            val movieItem = rule.activity.getString(R.string.movie_item)
            rule.onNodeWithContentDescription(movieItem).assertIsDisplayed()
        }
    }
}


