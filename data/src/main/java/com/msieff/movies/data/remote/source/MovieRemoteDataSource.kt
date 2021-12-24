package com.msieff.movies.data.remote.source

import com.google.gson.Gson
import com.msieff.movies.data.model.MovieEntity
import com.msieff.movies.data.remote.model.response.MoviesResponse
import com.msieff.movies.data.remote.model.response.VideosResponse
import com.msieff.movies.data.remote.service.MovieService
import com.msieff.movies.domain.model.common.State
import retrofit2.Retrofit
import javax.inject.Inject

/**
 * fetches data from remote source
 */
open class MovieRemoteDataSource @Inject constructor(
    private val retrofit: Retrofit,
    gson: Gson,
) : BaseDataSource(gson) {

    internal suspend fun fetchPopularMovies(): State<MoviesResponse> {
        val movieService = retrofit.create(MovieService::class.java);
        return getResponse(request = { movieService.getPopularMovies() })
    }

    internal suspend fun fetchTopRatedMovies(): State<MoviesResponse> {
        val movieService = retrofit.create(MovieService::class.java);
        return getResponse(request = { movieService.getTopRatedMovies() })
    }

    internal suspend fun getMovieVideos(id: Int): State<VideosResponse> {
        val movieService = retrofit.create(MovieService::class.java);
        return getResponse(request = { movieService.getVideos(id) })
    }

    internal suspend fun search(term: String): State<MoviesResponse> {
        val movieService = retrofit.create(MovieService::class.java);
        return getResponse(request = { movieService.search(term) })
    }
}