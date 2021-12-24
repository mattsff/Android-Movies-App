package com.msieff.movies.domain.repository

import com.msieff.movies.domain.model.TVShow
import com.msieff.movies.domain.model.Video
import com.msieff.movies.domain.model.common.State

/**
 * Repository which fetches data from Remote or Local data sources
 */
interface TVShowRepository {
    suspend fun fetchPopularTVShows(): State<List<TVShow>>?

    suspend fun fetchTopRatedTVShows(): State<List<TVShow>>?

    suspend fun getTVShowVideos(id: Int): State<List<Video>>?

    suspend fun search(term: String): State<List<TVShow>>
}