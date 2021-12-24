package com.msieff.movies.presentation.content_details

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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class ContentDetailsViewModel
@Inject constructor(
    val getMovieVideos: GetMovieVideos,
    val getTVShowVideos: GetTVShowVideos,
) : ViewModel() {
    lateinit var content: Content

    private val _videosState: MutableLiveData<State<List<Video>>?> = MutableLiveData()
    val videoState: LiveData<State<List<Video>>?> = _videosState

    private val _videos: MutableLiveData<List<Video>?> = MutableLiveData()
    val videos: LiveData<List<Video>?> = _videos

    fun loadData() {
        viewModelScope.launch {
            if (content is Movie)
                loadMovieVideos()
            else
                loadTVShowVideos()
        }
    }

    private suspend fun loadMovieVideos() {
        getMovieVideos(GetMovieVideos.Params(content.id)).collect {
            _videosState.postValue(it)

            val videos = if(it is State.Data) it.data else null
            _videos.postValue(videos)
        }
    }

    private suspend fun loadTVShowVideos() {
        getTVShowVideos(GetTVShowVideos.Params(content.id)).collect {
            _videosState.postValue(it)

            val videos = if(it is State.Data) it.data else null
            _videos.postValue(videos)
        }
    }
}