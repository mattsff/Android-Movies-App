package com.msieff.movies.app.di

import com.msieff.movies.data.local.AppDatabase
import com.msieff.movies.data.local.source.VideoLocalDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object VideoModule {

    @Singleton
    @Provides
    fun provideVideoLocalDataSource(appDatabase: AppDatabase): VideoLocalDataSource {
        return appDatabase.videoLocalDataSource()
    }
}