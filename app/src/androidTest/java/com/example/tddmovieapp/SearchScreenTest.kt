package com.example.tddmovieapp

import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.junit.Rule
import org.junit.Test

class SearchScreenTest {

    @get:Rule
    val searchScreenRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun performSearchMovies() {
        launchSearchScreen(searchScreenRule) {
            typeMovieTitle()
            search()
        } verify {
            searchedMoviesAreShown()
        }
    }

    private fun search() {
        TODO("Not yet implemented")
    }

    private fun typeMovieTitle() {
        TODO("Not yet implemented")
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
    fun searchedMoviesAreShown() {
        TODO("Not yet implemented")
    }
}


