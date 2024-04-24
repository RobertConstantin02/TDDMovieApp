package com.example.tddmovieapp

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.example.tddmovieapp.robot.launchSearchScreen
import org.junit.Rule
import org.junit.Test

class SearchScreenTest {

    @get:Rule
    val rule = createAndroidComposeRule<MainActivity>()

    @Test
    fun performSearchMovies() {

        launchSearchScreen(rule) {
            typeMovieTitle("iron man")
            search()
        } verify {
            searchedMoviesAreShown()
        }
    }
}