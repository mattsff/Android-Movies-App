package com.msieff.movies.data.model

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.msieff.movies.data.remote.common.ImageProvider
import com.msieff.movies.domain.model.Movie

@Entity(tableName = "movie")
data class MovieEntity(
    @NonNull
    @PrimaryKey
    val id: Int,
    val title: String?,
    val overview: String?,
    val popularity: Double,
    val vote_average: Double,
    val poster_path: String?,
    val backdrop_path: String?,
)

internal fun MovieEntity.toDomainModel(imageProvider: ImageProvider) =
    Movie(id,
        title,
        overview,
        popularity,
        vote_average,
        imageProvider.getFullPath(poster_path),
        imageProvider.getFullPath(backdrop_path))
