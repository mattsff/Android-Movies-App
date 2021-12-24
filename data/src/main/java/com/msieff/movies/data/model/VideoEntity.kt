package com.msieff.movies.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.msieff.movies.data.remote.common.ImageProvider
import com.msieff.movies.domain.model.Video

@Entity(tableName = "video")
data class VideoEntity(
    @PrimaryKey
    @SerializedName("id") val id: String,
    @SerializedName("key") val key: String,
    @SerializedName("name") val name: String,
    @SerializedName("published_at") val published_at: String,
    val content_id: Int,
)

internal fun VideoEntity.toDomainModel() =
    Video(id, key, name, published_at)
