package com.example.tddmovieapp

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.example.tddmovieapp.domain.di.SearchMovies
import com.example.tddmovieapp.domain.di.UseCaseModule
import com.example.tddmovieapp.domain.model.DomainResource
import com.example.tddmovieapp.domain.model.MovieBo
import com.example.tddmovieapp.domain.usecase.SearchMoviesUseCase
import com.example.tddmovieapp.domain.usecase.SearchMoviesUseCaseImpl
import com.example.tddmovieapp.domain.usecase.SearchMoviesUseCaseImplFake
import com.example.tddmovieapp.presentation.di.DispatcherIO
import com.example.tddmovieapp.presentation.di.DispatchersModule
import com.example.tddmovieapp.presentation.di.SearchQueryValidator
import com.example.tddmovieapp.presentation.di.ValidatorModule
import com.example.tddmovieapp.presentation.feature.util.Validator
import com.example.tddmovieapp.robot.launchSearchScreen
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.junit.Rule
import org.junit.Test
import javax.inject.Singleton

@HiltAndroidTest
@UninstallModules(
    UseCaseModule::class,
    ValidatorModule::class,
    DispatchersModule::class
)
class SearchScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val rule = createAndroidComposeRule<MainActivity>()

    val movies = listOf(
        MovieBo(4532, "Marvel: Avangers", 4.1, "https://m.media-amazon.com/images/I/81Q3-wGudPL._AC_UF1000,1000_QL80_.jpg"),
        MovieBo(5675, "Marvel: Black Panther", 5.0, "https://cdn.britannica.com/60/182360-050-CD8878D6/Avengers-Age-of-Ultron-Joss-Whedon.jpg")
    )

//    @BindValue
//    @JvmField
//    val searchMoviesUseCase: SearchMoviesUseCase = SearchMoviesUseCaseImplFake(DomainResource.Success(movies))

    @BindValue
    @JvmField
    val queryValidator: Validator<String> = QueryValidatorFake()

    @BindValue
    @JvmField
    val backgroundDispatcher: CoroutineDispatcher = Dispatchers.Unconfined


    @Test
    fun performSearchMovies() {
        launchSearchScreen(rule) {
            typeMovieTitle("marve")
            search()
        } verify {
            searchedMoviesAreShown()
        }
    }
}

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [UseCaseModule::class]
)
object FakeUseCaseModule {
    @Provides
    @Singleton
    fun bindGetFavoriteCharacters(): SearchMoviesUseCase = SearchMoviesUseCaseImplFake(DomainResource.Success(
        listOf(
            MovieBo(4532, "Marvel: Avangers", 4.1, "https://m.media-amazon.com/images/I/81Q3-wGudPL._AC_UF1000,1000_QL80_.jpg"),
            MovieBo(5675, "Marvel: Black Panther", 5.0, "https://cdn.britannica.com/60/182360-050-CD8878D6/Avengers-Age-of-Ultron-Joss-Whedon.jpg")
        )
    ))
}

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [ValidatorModule::class]
)
object FakeValidatorModule {
    @SearchQueryValidator
    @Provides
    @Singleton
    fun provideValidator(): Validator<String> = QueryValidatorFake()
}

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DispatchersModule::class]
)
object FakeCoroutineDispatcherModule {
    @DispatcherIO
    @Provides
    @Singleton
    fun provideBackgroundDispatcher(): CoroutineDispatcher = Dispatchers.Unconfined
}

class QueryValidatorFake : Validator<String> {
    var isValidQuery: Boolean = true
    override fun validate(data: String): Boolean {
        return isValidQuery
    }
}
