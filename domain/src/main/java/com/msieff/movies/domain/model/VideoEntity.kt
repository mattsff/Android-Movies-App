package com.msieff.movies.domain.model

data class Video(
    val id: String,
    val key: String,
    val name: String,
    val publishedAt: String
) {
    val trailerImagePath: String
        get() = "https://img.youtube.com/vi/$key/hqdefault.jpg"
    val youtubeLink: String
        get() = "https://www.youtube.com/watch?v=$key"
}
