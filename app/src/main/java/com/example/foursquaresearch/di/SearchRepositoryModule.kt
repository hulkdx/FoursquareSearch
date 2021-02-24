package com.example.foursquaresearch.di

import com.example.foursquaresearch.data.respository.SearchRepository
import com.example.foursquaresearch.data.respository.SearchRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
abstract class SearchRepositoryModule {
    @Binds
    abstract fun provideSearchRepository(impl: SearchRepositoryImpl): SearchRepository
}
