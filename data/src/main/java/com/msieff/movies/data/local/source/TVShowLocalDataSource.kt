package com.msieff.movies.data.local.source

import androidx.room.*
import com.msieff.movies.data.model.TVShowEntity

@Dao
interface TVShowLocalDataSource {

    @Query("SELECT * FROM tvshow order by popularity DESC")
    fun getPopular(): List<TVShowEntity>?

    @Query("SELECT * FROM tvshow order by vote_average DESC")
    fun getTopRated(): List<TVShowEntity>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(movies: List<TVShowEntity>)
}