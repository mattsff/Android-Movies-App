package com.msieff.movies.data.model

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.msieff.movies.data.remote.common.ImageProvider
import com.msieff.movies.domain.model.TVShow

@Entity(tableName = "tvshow")
data class TVShowEntity(
    @NonNull
    @PrimaryKey
    val id: Int,
    val name: String?,
    val overview: String?,
    val popularity: Double,
    val vote_average: Double,
    val poster_path: String?,
    val backdrop_path: String?,
    )

internal fun TVShowEntity.toDomainModel(imageProvider: ImageProvider) =
    TVShow(id,
        name,
        overview,
        popularity,
        vote_average,
        imageProvider.getFullPath(poster_path),
        imageProvider.getFullPath(backdrop_path))
