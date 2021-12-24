package com.msieff.movies.domain.use_case

import com.msieff.movies.domain.model.TVShow
import com.msieff.movies.domain.model.common.State
import com.msieff.movies.domain.repository.TVShowRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class SearchTVShows
@Inject constructor(
    private val tvShowRepository: TVShowRepository,
) : UseCase<Flow<State<List<TVShow>>?>, SearchTVShows.Params>() {

    data class Params(val searchTerm: String)

    override suspend fun run(params: Params): Flow<State<List<TVShow>>?> {
        return flow {
            emit(State.Loading())
            emit(tvShowRepository.search(params.searchTerm))
        }.flowOn(Dispatchers.IO)
    }
}