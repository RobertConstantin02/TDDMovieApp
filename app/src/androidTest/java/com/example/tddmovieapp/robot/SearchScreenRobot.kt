package com.example.tddmovieapp.robot

import androidx.compose.ui.test.assertAny
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.example.tddmovieapp.MainActivity
import com.example.tddmovieapp.R

fun launchSearchScreen(
    rule: AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>,
    block: SearchScreenRobot.() -> Unit
): SearchScreenRobot {
    return SearchScreenRobot(rule).apply(block)
}

class SearchScreenRobot(private val rule: AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>) {
    fun typeMovieTitle(searchInput: String) {
        val searchField = rule.activity.getString(R.string.search_field_hint)
        rule.onNodeWithContentDescription(searchField).performTextInput(searchInput)
    }

    fun search() {
        val searchButton = rule.activity.getString(R.string.search_button)
        rule.onNodeWithContentDescription(searchButton).performClick()
    }

    infix fun verify(block: SearchScreenVerification.() -> Unit): SearchScreenVerification {
        return SearchScreenVerification(rule).apply(block)
    }
}

class SearchScreenVerification(private val rule: AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>) {
    fun searchedMoviesAreShown() {
        val list = rule.activity.getString(R.string.search_screen_lazy_column)
        val movie = rule.activity.getString(R.string.movie_item)
        rule.onNodeWithContentDescription(list).onChildren().assertAny(hasContentDescription(movie))
    }
}