package com.msieff.movies.domain.use_case

import com.msieff.movies.domain.model.Video
import com.msieff.movies.domain.model.common.State
import com.msieff.movies.domain.repository.TVShowRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetTVShowVideos
@Inject constructor(
    private val tvShowsRepository: TVShowRepository,
) : UseCase<Flow<State<List<Video>>?>, GetTVShowVideos.Params>() {

    data class Params(val tvShowId: Int)

    override suspend fun run(params: Params): Flow<State<List<Video>>?> {
        return flow {
            emit(State.Loading())
            emit(tvShowsRepository.getTVShowVideos(params.tvShowId))
        }.flowOn(Dispatchers.IO)
    }
}