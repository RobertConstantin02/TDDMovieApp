package com.example.tddmovieapp.presentation.di

import com.example.tddmovieapp.presentation.feature.util.QueryValidator
import com.example.tddmovieapp.presentation.feature.util.Validator
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier
import javax.inject.Singleton


@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class SearchQueryValidator

@Module
@InstallIn(SingletonComponent::class)
object ValidatorModule {

    @SearchQueryValidator
    @Provides
    @Singleton
    fun provideValidator(): Validator<String> = QueryValidator()
}

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class DispatcherIO
@Module
@InstallIn(SingletonComponent::class)
class DispatchersModule {
    @Provides
    @Singleton
    @DispatcherIO
    fun provideDispatcherIO(): CoroutineDispatcher = Dispatchers.IO
}