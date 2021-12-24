package com.msieff.movies.data.remote.source

import com.google.gson.Gson
import com.msieff.movies.data.model.TVShowEntity
import com.msieff.movies.data.remote.model.response.MoviesResponse
import com.msieff.movies.data.remote.model.response.TVShowsResponse
import com.msieff.movies.data.remote.model.response.VideosResponse
import com.msieff.movies.data.remote.service.MovieService
import com.msieff.movies.data.remote.service.TVShowService
import com.msieff.movies.domain.model.common.State
import retrofit2.Retrofit
import javax.inject.Inject

/**
 * fetches data from remote source
 */
class TVShowRemoteDataSource @Inject constructor(
    private val retrofit: Retrofit,
    gson: Gson,
) : BaseDataSource(gson) {

    internal suspend fun fetchPopularTVShows(): State<TVShowsResponse> {
        val tvService = retrofit.create(TVShowService::class.java);
        return getResponse(request = { tvService.getPopularTVShows() })
    }

    internal suspend fun fetchTopRatedTVShows(): State<TVShowsResponse> {
        val tvService = retrofit.create(TVShowService::class.java);
        return getResponse(request = { tvService.getTopRatedTVShows() })
    }

    internal suspend fun getVideos(id: Int): State<VideosResponse> {
        val tvService = retrofit.create(TVShowService::class.java);
        return getResponse(request = { tvService.getVideos(id) })
    }

    internal suspend fun search(term: String): State<TVShowsResponse> {
        val tvService = retrofit.create(TVShowService::class.java);
        return getResponse(request = { tvService.search(term) })
    }
}