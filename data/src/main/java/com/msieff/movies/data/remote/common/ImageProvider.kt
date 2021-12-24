package com.msieff.movies.data.remote.common

class ImageProvider(private val baseUrl: String) {
    fun getFullPath(relativePath: String?): String? {
        return relativePath?.let { "$baseUrl/$relativePath" }
    }
}