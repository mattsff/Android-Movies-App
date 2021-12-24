package com.msieff.movies.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.msieff.movies.data.local.source.MovieLocalDataSource
import com.msieff.movies.data.local.source.TVShowLocalDataSource
import com.msieff.movies.data.local.source.VideoLocalDataSource
import com.msieff.movies.data.model.MovieEntity
import com.msieff.movies.data.model.TVShowEntity
import com.msieff.movies.data.model.VideoEntity

@Database(entities = [MovieEntity::class, TVShowEntity::class, VideoEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieLocalDataSource(): MovieLocalDataSource

    abstract fun tvShowsLocalDataSource(): TVShowLocalDataSource

    abstract fun videoLocalDataSource(): VideoLocalDataSource

}