package com.msieff.movies.app.di

import com.google.gson.Gson
import com.msieff.movies.data.local.AppDatabase
import com.msieff.movies.data.local.source.MovieLocalDataSource
import com.msieff.movies.data.local.source.VideoLocalDataSource
import com.msieff.movies.data.remote.common.ImageProvider
import com.msieff.movies.data.remote.source.MovieRemoteDataSource
import com.msieff.movies.data.repository.MovieRepositoryImpl
import com.msieff.movies.domain.repository.MovieRepository
import com.msieff.movies.domain.repository.TVShowRepository
import com.msieff.movies.domain.use_case.DiscoverMoviesUseCase
import com.msieff.movies.domain.use_case.GetMovieVideos
import com.msieff.movies.domain.use_case.GetTVShowVideos
import com.msieff.movies.domain.use_case.SearchMovies
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object MovieModule {

    @Singleton
    @Provides
    fun provideMovieLocalDataSource(appDatabase: AppDatabase): MovieLocalDataSource {
        return appDatabase.movieLocalDataSource()
    }

    @Singleton
    @Provides
    fun provideMovieRemoteDataSource(retrofit: Retrofit, gson: Gson): MovieRemoteDataSource {
        return MovieRemoteDataSource(retrofit, gson)
    }

    @Singleton
    @Provides
    fun provideMovieRepository(
        movieRemoteDataSource: MovieRemoteDataSource,
        movieLocalDataSource: MovieLocalDataSource,
        videoLocalDataSource: VideoLocalDataSource,
        imageProvider: ImageProvider,
    ): MovieRepository {
        return MovieRepositoryImpl(movieRemoteDataSource, movieLocalDataSource, videoLocalDataSource, imageProvider)
    }

    @Provides
    @Singleton
    fun provideDiscoverMoviesContentUseCase(moviesRepository: MovieRepository): DiscoverMoviesUseCase {
        return DiscoverMoviesUseCase(moviesRepository)
    }

    @Provides
    @Singleton
    fun provideGetMovieVideosUseCase(moviesRepository: MovieRepository): GetMovieVideos {
        return GetMovieVideos(moviesRepository)
    }

    @Provides
    @Singleton
    fun provideSearchMoviesUseCase(moviesRepository: MovieRepository): SearchMovies {
        return SearchMovies(moviesRepository)
    }
}