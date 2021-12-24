package com.msieff.movies.presentation.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msieff.movies.domain.model.Content
import com.msieff.movies.domain.model.Movie
import com.msieff.movies.domain.model.Video
import com.msieff.movies.domain.model.common.State
import com.msieff.movies.domain.use_case.GetMovieVideos
import com.msieff.movies.domain.use_case.GetTVShowVideos
import com.msieff.movies.domain.use_case.SearchMovies
import com.msieff.movies.domain.use_case.SearchTVShows
import com.msieff.movies.presentation.model.ContentType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class SearchViewModel
@Inject constructor(
    val searchMovies: SearchMovies,
    val searchTVShows: SearchTVShows,
) : ViewModel() {

    var contentType: ContentType = ContentType.MOVIES

    private val _contents: MutableLiveData<State<List<Content>>?> = MutableLiveData()
    val contentsState: LiveData<State<List<Content>>?> = _contents

    val searchTerm:  MutableLiveData<String> = MutableLiveData()

    private var searchJob: Job? = null

    fun search() {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            // Debounce to avoid multiple search requests while is typing
            delay(750)
            if (contentType == ContentType.MOVIES)
                searchMovies()
            else
                searchVideos()
        }
    }


    private suspend fun searchMovies() {
        searchTerm.value?.let { term ->
            searchMovies(SearchMovies.Params(term)).collect {
                _contents.postValue(it)
            }
        }
    }

    private suspend fun searchVideos() {
        searchTerm.value?.let { term ->
            searchTVShows(SearchTVShows.Params(term)).collect {
                _contents.postValue(it)
            }
        }
    }
}