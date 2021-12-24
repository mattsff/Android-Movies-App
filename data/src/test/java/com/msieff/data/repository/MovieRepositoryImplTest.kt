package com.msieff.data.repository

import com.msieff.data.DataTest
import com.msieff.movies.data.local.source.MovieLocalDataSource
import com.msieff.movies.data.local.source.VideoLocalDataSource
import com.msieff.movies.data.model.toDomainModel
import com.msieff.movies.data.remote.common.ImageProvider
import com.msieff.movies.data.remote.model.response.MoviesResponse
import com.msieff.movies.data.remote.source.MovieRemoteDataSource
import com.msieff.movies.data.repository.MovieRepositoryImpl
import com.msieff.movies.domain.model.common.State
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class MovieRepositoryImplTest {
    @MockK
    internal lateinit var mockMovieRemoteDataSource: MovieRemoteDataSource

    @MockK
    internal lateinit var mockMovieLocalDataSource: MovieLocalDataSource

    @MockK
    internal lateinit var mockVideoLocalDataSource: VideoLocalDataSource

    internal var mockImageProvider = ImageProvider("")

    private lateinit var repository: MovieRepositoryImpl

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        repository = MovieRepositoryImpl(mockMovieRemoteDataSource,
            mockMovieLocalDataSource,
            mockVideoLocalDataSource,
            mockImageProvider)
    }

    @Test
    fun `fetchPopularMovies fetches a list of movies and maps to domain `() {
        // given
        coEvery {
            mockMovieRemoteDataSource.fetchPopularMovies()
        } returns State.Data(MoviesResponse(DataTest.getMovies()))

        every {
            mockMovieLocalDataSource.insertAll(any())
        } just Runs

        val expectedMovies = DataTest.getMovies().map { it.toDomainModel(mockImageProvider) }

        // when
        val result = runBlocking { repository.fetchPopularMovies() }

        // then
        assertEquals(expectedMovies, (result as State.Data).data)
    }

    @Test
    fun `fetchPopularMovies should update database`() {
        // given

        val movies = DataTest.getMovies()
        coEvery {
            mockMovieRemoteDataSource.fetchPopularMovies()
        } returns State.Data(MoviesResponse(movies))

        every {
            mockMovieLocalDataSource.insertAll(movies)
        } just Runs

        // when
        runBlocking { repository.fetchPopularMovies() }

        // then
        verify(exactly = 1) { mockMovieLocalDataSource.insertAll(movies) }
    }

    @Test
    fun `fetchPopularMovies return data from database if could not be fetched from remote`() {
        // given
        val moviesEntities = DataTest.getMovies()
        val expected = moviesEntities.map { it.toDomainModel(mockImageProvider) }

        coEvery { mockMovieRemoteDataSource.fetchPopularMovies() } returns State.Error(Exception())
        coEvery { mockMovieLocalDataSource.insertAll(any()) } just Runs
        coEvery { mockMovieLocalDataSource.getPopular() } returns moviesEntities

        // when
        val result = runBlocking { repository.fetchPopularMovies() }

        // then
        assertEquals(expected, (result as State.Data).data)
    }

    @Test
    fun `fetchTopRatedMovies fetches a list of movies and maps to domain `() {
        // given
        coEvery {
            mockMovieRemoteDataSource.fetchTopRatedMovies()
        } returns State.Data(MoviesResponse(DataTest.getMovies()))

        every {
            mockMovieLocalDataSource.insertAll(any())
        } just Runs

        val expectedMovies = DataTest.getMovies().map { it.toDomainModel(mockImageProvider) }

        // when
        val result = runBlocking { repository.fetchTopRatedMovies() }

        // then
        assertEquals(expectedMovies, (result as State.Data).data)
    }

    @Test
    fun `fetchTopRatedMovies should update database`() {
        // given

        val movies = DataTest.getMovies()
        coEvery {
            mockMovieRemoteDataSource.fetchTopRatedMovies()
        } returns State.Data(MoviesResponse(movies))

        every {
            mockMovieLocalDataSource.insertAll(movies)
        } just Runs

        // when
        runBlocking { repository.fetchTopRatedMovies() }

        // then
        verify(exactly = 1) { mockMovieLocalDataSource.insertAll(movies) }
    }

    @Test
    fun `fetchTopRatedMovies return data from database if could not be fetched from remote`() {
        // given
        val moviesEntities = DataTest.getMovies()
        val expected = moviesEntities.map { it.toDomainModel(mockImageProvider) }

        coEvery { mockMovieRemoteDataSource.fetchTopRatedMovies() } returns State.Error(Exception())
        coEvery { mockMovieLocalDataSource.insertAll(any()) } just Runs
        coEvery { mockMovieLocalDataSource.getTopRated() } returns moviesEntities

        // when
        val result = runBlocking { repository.fetchTopRatedMovies() }

        // then
        assertEquals(expected, (result as State.Data).data)
    }
}