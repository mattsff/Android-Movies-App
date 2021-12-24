package com.msieff.movies.data.remote.service

import com.msieff.movies.data.model.TVShowEntity
import com.msieff.movies.data.remote.model.response.MoviesResponse
import com.msieff.movies.data.remote.model.response.TVShowsResponse
import com.msieff.movies.data.remote.model.response.VideosResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Retrofit API Service
 */
internal interface TVShowService {

    @GET("/3/tv/popular")
    suspend fun getPopularTVShows() : Response<TVShowsResponse>

    @GET("/3/tv/top_rated")
    suspend fun getTopRatedTVShows() : Response<TVShowsResponse>

    @GET("/3/tv/{tv_id}/videos")
    suspend fun getVideos(@Path("tv_id") id: Int): Response<VideosResponse>

    @GET("/3/search/tv")
    suspend fun search(@Query("query") term: String): Response<TVShowsResponse>

}