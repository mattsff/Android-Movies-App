package com.msieff.movies.data.remote.model.response

import com.msieff.movies.data.model.VideoEntity

internal class VideosResponse(
    val results: List<VideoEntity>?
)