package com.msieff.movies.app.di

import com.google.gson.Gson
import com.msieff.movies.data.local.AppDatabase
import com.msieff.movies.data.local.source.TVShowLocalDataSource
import com.msieff.movies.data.local.source.VideoLocalDataSource
import com.msieff.movies.data.remote.common.ImageProvider
import com.msieff.movies.data.remote.source.TVShowRemoteDataSource
import com.msieff.movies.data.repository.TVShowRepositoryImpl
import com.msieff.movies.domain.repository.MovieRepository
import com.msieff.movies.domain.repository.TVShowRepository
import com.msieff.movies.domain.use_case.DiscoverTVShowsUseCase
import com.msieff.movies.domain.use_case.GetTVShowVideos
import com.msieff.movies.domain.use_case.SearchMovies
import com.msieff.movies.domain.use_case.SearchTVShows
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object TVShowsModule {

    @Singleton
    @Provides
    fun provideTVShowLocalDataSource(appDatabase: AppDatabase): TVShowLocalDataSource {
        return appDatabase.tvShowsLocalDataSource()
    }

    @Singleton
    @Provides
    fun provideTVShowRemoteDataSource(retrofit: Retrofit, gson: Gson): TVShowRemoteDataSource {
        return TVShowRemoteDataSource(retrofit, gson)
    }

    @Singleton
    @Provides
    fun provideTVShowRepository(
        tvShowRemoteDataSource: TVShowRemoteDataSource,
        tvShowLocalDataSource: TVShowLocalDataSource,
        videoLocalDataSource: VideoLocalDataSource,
        imageProvider: ImageProvider,
    ): TVShowRepository {
        return TVShowRepositoryImpl(tvShowRemoteDataSource, tvShowLocalDataSource, videoLocalDataSource, imageProvider)
    }

    @Provides
    @Singleton
    fun provideDiscoverTVShowsContentUseCase(tvShowsRepository: TVShowRepository): DiscoverTVShowsUseCase {
        return DiscoverTVShowsUseCase(tvShowsRepository)
    }

    @Provides
    @Singleton
    fun provideGetTVShowVideosUseCase(tvShowsRepository: TVShowRepository): GetTVShowVideos {
        return GetTVShowVideos(tvShowsRepository)
    }

    @Provides
    @Singleton
    fun provideSearchTVShowsUseCase(tvShowsRepository: TVShowRepository): SearchTVShows {
        return SearchTVShows(tvShowsRepository)
    }
}