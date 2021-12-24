package com.msieff.movies.data.remote.model.response

import com.msieff.movies.data.model.MovieEntity

internal class MoviesResponse(
    val results: List<MovieEntity>?
)