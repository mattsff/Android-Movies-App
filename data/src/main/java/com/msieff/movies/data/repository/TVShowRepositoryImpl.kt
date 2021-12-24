package com.msieff.movies.data.repository

import com.msieff.movies.data.local.source.TVShowLocalDataSource
import com.msieff.movies.data.local.source.VideoLocalDataSource
import com.msieff.movies.data.model.toDomainModel
import com.msieff.movies.data.remote.common.ImageProvider
import com.msieff.movies.data.remote.source.TVShowRemoteDataSource
import com.msieff.movies.domain.model.Movie
import com.msieff.movies.domain.model.TVShow
import com.msieff.movies.domain.model.Video
import com.msieff.movies.domain.model.common.State
import com.msieff.movies.domain.repository.TVShowRepository
import javax.inject.Inject

/**
 * Repository which fetches data from Remote or Local data sources
 */
class TVShowRepositoryImpl @Inject constructor(
    private val tvShowRemoteDataSource: TVShowRemoteDataSource,
    private val tvShowLocalDataSource: TVShowLocalDataSource,
    private val videosLocalDataSource: VideoLocalDataSource,
    private val imageProvider: ImageProvider,
) : TVShowRepository {

    /**
     *  Get popular tvShows from remote or cache if it's not available
     */
    override suspend fun fetchPopularTVShows(): State<List<TVShow>>? {
        val state = tvShowRemoteDataSource.fetchPopularTVShows()

        return if (state is State.Data && state.data?.results != null) {
            // Cache to database and return results if response is successful
            val results = state.data!!.results!!
            tvShowLocalDataSource.insertAll(results)
            State.Data(results.map { it.toDomainModel(imageProvider) })
        } else
        // Get from cache
            fetchCachedPopularTVShows()
    }

    private fun fetchCachedPopularTVShows(): State<List<TVShow>>? =
        tvShowLocalDataSource.getPopular()?.let { results ->
            State.Data(results.map {
                it.toDomainModel(imageProvider)
            })
        }


    /**
     *  Get top rated tvShows from remote or cache if it's not available
     */
    override suspend fun fetchTopRatedTVShows(): State<List<TVShow>>? {
        val state = tvShowRemoteDataSource.fetchTopRatedTVShows()

        return if (state is State.Data && state.data?.results != null) {
            // Cache to database and return results if response is successful
            val results = state.data!!.results!!
            tvShowLocalDataSource.insertAll(results)
            State.Data(results.map { it.toDomainModel(imageProvider) })
        } else
        // Get from cache
            fetchCachedTopRatedTVShows()
    }

    private fun fetchCachedTopRatedTVShows(): State<List<TVShow>>? =
        tvShowLocalDataSource.getTopRated()?.let { results ->
            State.Data(results.map { it.toDomainModel(imageProvider) })
        }

    override suspend fun getTVShowVideos(movieId: Int): State<List<Video>>? {
        var state = tvShowRemoteDataSource.getVideos(movieId)

        return if (state is State.Data && state.data?.results != null) {
            // Cache to database and return results if response is successful
            val results = state.data!!.results!!.map {
                it.copy(
                    content_id = movieId
                )
            }
            videosLocalDataSource.insertAll(results)
            State.Data(results.map { it.toDomainModel() })
        } else
        // Get from cache
            fetchCachedVideos(movieId)
    }

    private fun fetchCachedVideos(movieId: Int): State<List<Video>>? =
        videosLocalDataSource.getByContentId(movieId)?.let { results ->
            State.Data(results.map { it.toDomainModel() })
        }

    override suspend fun search(term: String): State<List<TVShow>> {
        val state = tvShowRemoteDataSource.search(term)

        return if (state is State.Data && state.data?.results != null) {
            val results = state.data!!.results!!
            State.Data(results.map { it.toDomainModel(imageProvider) })
        } else
            (state as State<List<TVShow>>)
    }
}