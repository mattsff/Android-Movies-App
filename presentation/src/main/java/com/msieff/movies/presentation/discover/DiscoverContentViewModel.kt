package com.msieff.movies.presentation.discover

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msieff.movies.domain.model.ContentCategory
import com.msieff.movies.domain.model.common.State
import com.msieff.movies.domain.use_case.DiscoverMoviesUseCase
import com.msieff.movies.domain.use_case.DiscoverTVShowsUseCase
import com.msieff.movies.domain.use_case.UseCase
import com.msieff.movies.presentation.model.ContentType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class DiscoverContentViewModel
@Inject constructor(
    private val discoverMoviesUseCase: DiscoverMoviesUseCase,
    private val discoverTVShowsUseCase: DiscoverTVShowsUseCase,
) : ViewModel() {

    lateinit var contentType: ContentType

    private val _categoriesState: MutableLiveData<State<List<ContentCategory>>?> = MutableLiveData()
    val categoriesState: LiveData<State<List<ContentCategory>>?> = _categoriesState

    fun loadData() {
        viewModelScope.launch {
            if (contentType == ContentType.MOVIES)
                loadMovies()
            else
                loadTVShows()
        }
    }

    private suspend fun loadMovies() {
        discoverMoviesUseCase(UseCase.None()).collect {
            _categoriesState.postValue(it)
        }
    }

    private suspend fun loadTVShows() {
        discoverTVShowsUseCase(UseCase.None()).collect {
            _categoriesState.postValue(it)
        }
    }
}