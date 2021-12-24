package com.msieff.data

import com.msieff.movies.data.model.MovieEntity
import com.msieff.movies.data.model.TVShowEntity

object DataTest {

    internal fun getMovie(
        id: Int = 100,
        title: String = "title",
        overview: String = "overview",
        popularity: Double = 80.0,
        rating: Double = 3.5,
        posterPath: String = "image_123.jpg",
        coverPath: String = "cover_123.jpg",
    ): MovieEntity =
        MovieEntity(id, title, overview, popularity, rating, posterPath, coverPath)

    internal fun getMovies() = listOf(
        getMovie(id = 500),
        getMovie(id = 400),
        getMovie(id = 200)

    )

    internal fun getTVShow(
        id: Int = 100,
        title: String = "TV Show",
        overview: String = "TV show - overview",
        popularity: Double = 50.0,
        rating: Double = 4.0,
        posterPath: String = "image_tv_123.jpg",
        coverPath: String = "cover_tv_123.jpg",
    ): TVShowEntity =
        TVShowEntity(id, title, overview, popularity, rating, posterPath, coverPath)

    internal fun getTVShows() = listOf(
        getTVShow(id = 500)
    )
}