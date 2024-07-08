package com.example.tddmovieapp.data.di

import com.example.tddmovieapp.data.repository.MoviesRepository
import com.example.tddmovieapp.domain.di.QMoviesRepository
import com.example.tddmovieapp.domain.repository.IMoviesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @QMoviesRepository
    @Singleton
    @Binds
    fun bindLocationRepository(implementation: MoviesRepository): IMoviesRepository

}