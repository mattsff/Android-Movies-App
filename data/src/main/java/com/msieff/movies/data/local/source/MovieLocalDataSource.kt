package com.msieff.movies.data.local.source

import androidx.room.*
import com.msieff.movies.data.model.MovieEntity

@Dao
interface MovieLocalDataSource {

    @Query("SELECT * FROM movie order by popularity DESC")
    fun getPopular(): List<MovieEntity>?

    @Query("SELECT * FROM movie order by vote_average DESC")
    fun getTopRated(): List<MovieEntity>?

    @Query("SELECT * FROM movie order by vote_average DESC")
    fun getAll(): List<MovieEntity>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(movies: List<MovieEntity>)

}