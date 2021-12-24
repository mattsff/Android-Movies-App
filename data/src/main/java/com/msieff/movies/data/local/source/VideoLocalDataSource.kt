package com.msieff.movies.data.local.source

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.msieff.movies.data.model.VideoEntity

@Dao
interface VideoLocalDataSource {

    @Query("SELECT * FROM video WHERE content_id=:contentId order by published_at DESC")
    fun getByContentId(contentId: Int): List<VideoEntity>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(movies: List<VideoEntity>)
}