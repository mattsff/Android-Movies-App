package com.msieff.data.local.source

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.msieff.data.DataTest
import com.msieff.movies.data.local.AppDatabase
import com.msieff.movies.data.local.source.MovieLocalDataSource
import com.msieff.movies.domain.model.common.State
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class MovieLocalDataSourceTest {
    private lateinit var wordRoomDatabase: AppDatabase
    private lateinit var wordDao: MovieLocalDataSource

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        wordRoomDatabase =
            Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).allowMainThreadQueries()
                .build()
        wordDao = wordRoomDatabase.movieLocalDataSource()

    }

    @After
    fun closeDb() {
        wordRoomDatabase.close()
    }

    @Test
    fun `insertAll save a list of movies and getAll should return them`() {
        val movies = DataTest.getMovies()
        val expected = movies.size
        wordDao.insertAll(movies)
        assertEquals(expected, wordDao.getAll()!!.size)
    }

    @Test
    fun `getPopular should return all movies sort by popularity desc`() {
        val movie1 = DataTest.getMovie(
            popularity = 50.0
        )
        val movie2 = DataTest.getMovie(
            popularity = 100.0
        )
        val movie3 = DataTest.getMovie(
            popularity = 20.0
        )
        val movies = listOf(movie1, movie2, movie3)
        val expected = listOf(movie2, movie1, movie3)

        wordDao.insertAll(movies)
        assertEquals(expected, wordDao.getPopular())
    }
}