package com.msieff.data.repository

import com.msieff.data.DataTest
import com.msieff.movies.data.local.source.TVShowLocalDataSource
import com.msieff.movies.data.local.source.VideoLocalDataSource
import com.msieff.movies.data.model.toDomainModel
import com.msieff.movies.data.remote.common.ImageProvider
import com.msieff.movies.data.remote.model.response.TVShowsResponse
import com.msieff.movies.data.remote.source.TVShowRemoteDataSource
import com.msieff.movies.data.repository.TVShowRepositoryImpl
import com.msieff.movies.domain.model.common.State
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class TVShowRepositoryImplTest {
    @MockK
    internal lateinit var mockTVShowRemoteDataSource: TVShowRemoteDataSource

    @MockK
    internal lateinit var mockTVShowLocalDataSource: TVShowLocalDataSource

    @MockK
    internal lateinit var mockVideoLocalDataSource: VideoLocalDataSource

    internal var mockImageProvider = ImageProvider("")

    private lateinit var repository: TVShowRepositoryImpl

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        repository = TVShowRepositoryImpl(mockTVShowRemoteDataSource,
            mockTVShowLocalDataSource,
            mockVideoLocalDataSource,
            mockImageProvider)
    }

    @Test
    fun `fetchPopularTVShows fetches a list of tv shows and maps to domain `() {
        // given
        coEvery {
            mockTVShowRemoteDataSource.fetchPopularTVShows()
        } returns State.Data(TVShowsResponse(DataTest.getTVShows()))

        every {
            mockTVShowLocalDataSource.insertAll(any())
        } just Runs

        val expectedTVShows = DataTest.getTVShows().map { it.toDomainModel(mockImageProvider) }

        // when
        val result = runBlocking { repository.fetchPopularTVShows() }

        // then
        assertEquals(expectedTVShows, (result as State.Data).data)
    }

    @Test
    fun `fetchPopularTVShows should update database`() {
        // given

        val tvShows = DataTest.getTVShows()
        coEvery {
            mockTVShowRemoteDataSource.fetchPopularTVShows()
        } returns State.Data(TVShowsResponse(tvShows))

        every {
            mockTVShowLocalDataSource.insertAll(tvShows)
        } just Runs

        // when
        runBlocking { repository.fetchPopularTVShows() }

        // then
        verify(exactly = 1) { mockTVShowLocalDataSource.insertAll(tvShows) }
    }

    @Test
    fun `fetchPopularTVShows return data from database if could not be fetched from remote`() {
        // given
        val tvShowsEntities = DataTest.getTVShows()
        val expected = tvShowsEntities.map { it.toDomainModel(mockImageProvider) }

        coEvery { mockTVShowRemoteDataSource.fetchPopularTVShows() } returns State.Error(Exception())
        coEvery { mockTVShowLocalDataSource.insertAll(any()) } just Runs
        coEvery { mockTVShowLocalDataSource.getPopular() } returns tvShowsEntities

        // when
        val result = runBlocking { repository.fetchPopularTVShows() }

        // then
        assertEquals(expected, (result as State.Data).data)
    }

    @Test
    fun `fetchTopRatedTVShows fetches a list of tv shows and maps to domain `() {
        // given
        coEvery {
            mockTVShowRemoteDataSource.fetchTopRatedTVShows()
        } returns State.Data(TVShowsResponse(DataTest.getTVShows()))

        every {
            mockTVShowLocalDataSource.insertAll(any())
        } just Runs

        val expectedTVShows = DataTest.getTVShows().map { it.toDomainModel(mockImageProvider) }

        // when
        val result = runBlocking { repository.fetchTopRatedTVShows() }

        // then
        assertEquals(expectedTVShows, (result as State.Data).data)
    }

    @Test
    fun `fetchTopRatedTVShows should update database`() {
        // given

        val tvShows = DataTest.getTVShows()
        coEvery {
            mockTVShowRemoteDataSource.fetchTopRatedTVShows()
        } returns State.Data(TVShowsResponse(tvShows))

        every {
            mockTVShowLocalDataSource.insertAll(tvShows)
        } just Runs

        // when
        runBlocking { repository.fetchTopRatedTVShows() }

        // then
        verify(exactly = 1) { mockTVShowLocalDataSource.insertAll(tvShows) }
    }

    @Test
    fun `fetchTopRatedTVShows return data from database if could not be fetched from remote`() {
        // given
        val tvShowsEntities = DataTest.getTVShows()
        val expected = tvShowsEntities.map { it.toDomainModel(mockImageProvider) }

        coEvery { mockTVShowRemoteDataSource.fetchTopRatedTVShows() } returns State.Error(Exception())
        coEvery { mockTVShowLocalDataSource.insertAll(any()) } just Runs
        coEvery { mockTVShowLocalDataSource.getTopRated() } returns tvShowsEntities

        // when
        val result = runBlocking { repository.fetchTopRatedTVShows() }

        // then
        assertEquals(expected, (result as State.Data).data)
    }
}