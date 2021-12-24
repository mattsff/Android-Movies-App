package com.msieff.movies.data.repository

import com.msieff.movies.data.local.source.MovieLocalDataSource
import com.msieff.movies.data.local.source.VideoLocalDataSource
import com.msieff.movies.data.model.toDomainModel
import com.msieff.movies.data.remote.common.ImageProvider
import com.msieff.movies.data.remote.source.MovieRemoteDataSource
import com.msieff.movies.domain.model.Movie
import com.msieff.movies.domain.model.Video
import com.msieff.movies.domain.model.common.State
import com.msieff.movies.domain.repository.MovieRepository
import javax.inject.Inject

/**
 * Repository which fetches data from Remote or Local data sources
 */
class MovieRepositoryImpl @Inject constructor(
    private val movieRemoteDataSource: MovieRemoteDataSource,
    private val movieLocalDataSource: MovieLocalDataSource,
    private val videosLocalDataSource: VideoLocalDataSource,
    private val imageProvider: ImageProvider,
) : MovieRepository {

    /**
     *  Get popular movies from remote or cache if it's not available
     */
    override suspend fun fetchPopularMovies(): State<List<Movie>>? {
        val state = movieRemoteDataSource.fetchPopularMovies()

        return if (state is State.Data && state.data?.results != null) {
            // Cache to database and return results if response is successful
            val results = state.data!!.results!!
            movieLocalDataSource.insertAll(results)
            State.Data(results.map { it.toDomainModel(imageProvider) })
        } else
        // Get from cache
            fetchCachedPopularMovies()
    }

    private fun fetchCachedPopularMovies(): State<List<Movie>>? =
        movieLocalDataSource.getPopular()?.let { results ->
            State.Data(results.map {
                it.toDomainModel(imageProvider)
            })
        }


    /**
     *  Get top rated movies from remote or cache if it's not available
     */
    override suspend fun fetchTopRatedMovies(): State<List<Movie>>? {
        val state = movieRemoteDataSource.fetchTopRatedMovies()

        return if (state is State.Data && state.data?.results != null) {
            // Cache to database and return results if response is successful
            val results = state.data!!.results!!
            movieLocalDataSource.insertAll(results)
            State.Data(results.map { it.toDomainModel(imageProvider) })
        } else
        // Get from cache
            fetchCachedTopRatedMovies()
    }

    private fun fetchCachedTopRatedMovies(): State<List<Movie>>? =
        movieLocalDataSource.getTopRated()?.let { results ->
            State.Data(results.map { it.toDomainModel(imageProvider) })
        }

    override suspend fun getMovieVideos(movieId: Int): State<List<Video>>? {
        var state = movieRemoteDataSource.getMovieVideos(movieId)

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

    override suspend fun search(term: String): State<List<Movie>> {
        val state = movieRemoteDataSource.search(term)

        return if (state is State.Data && state.data?.results != null) {
            val results = state.data!!.results!!
            State.Data(results.map { it.toDomainModel(imageProvider) })
        } else
            (state as State<List<Movie>>)
    }
}