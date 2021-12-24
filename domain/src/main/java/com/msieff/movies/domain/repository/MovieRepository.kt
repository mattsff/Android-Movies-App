package com.msieff.movies.domain.repository

import com.msieff.movies.domain.model.Movie
import com.msieff.movies.domain.model.Video
import com.msieff.movies.domain.model.common.State

/**
 * Repository which fetches data from Remote or Local data sources
 */
interface MovieRepository {

    suspend fun fetchPopularMovies(): State<List<Movie>>?

    suspend fun fetchTopRatedMovies(): State<List<Movie>>?

    suspend fun getMovieVideos(id: Int): State<List<Video>>?

    suspend fun search(term: String): State<List<Movie>>
}