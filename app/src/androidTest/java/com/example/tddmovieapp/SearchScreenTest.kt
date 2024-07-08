package com.example.tddmovieapp

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.example.tddmovieapp.domain.di.UseCaseModule
import com.example.tddmovieapp.domain.model.DomainResource
import com.example.tddmovieapp.domain.model.MovieBo
import com.example.tddmovieapp.domain.usecase.SearchMoviesUseCase
import com.example.tddmovieapp.domain.usecase.SearchMoviesUseCaseImplFake
import com.example.tddmovieapp.robot.launchSearchScreen
import dagger.Module
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(UseCaseModule::class)
class SearchScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val rule = createAndroidComposeRule<MainActivity>()

    val movies = listOf(
        MovieBo(4532, "Marvel: Avangers", 4.1, "imageUrl1"),
        MovieBo(5675, "Marvel: Black Panther", 5.0, "imageUrl2")
    )

    @BindValue
    @JvmField
    val searchMoviesUseCase: SearchMoviesUseCase =
        SearchMoviesUseCaseImplFake(DomainResource.Success(movies))

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