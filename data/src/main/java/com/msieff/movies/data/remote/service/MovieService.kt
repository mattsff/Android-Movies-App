package com.msieff.movies.data.remote.service

import com.msieff.movies.data.model.MovieEntity
import com.msieff.movies.data.remote.model.response.MoviesResponse
import com.msieff.movies.data.remote.model.response.VideosResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Retrofit API Service
 */
internal interface MovieService {

    @GET("/3/movie/popular")
    suspend fun getPopularMovies(): Response<MoviesResponse>

    @GET("/3/movie/top_rated")
    suspend fun getTopRatedMovies(): Response<MoviesResponse>

    @GET("/3/movie/{movie_id}/videos")
    suspend fun getVideos(@Path("movie_id") id: Int): Response<VideosResponse>

    @GET("/3/search/movie")
    suspend fun search(@Query("query") term: String): Response<MoviesResponse>

}