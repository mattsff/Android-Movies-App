package com.msieff.movies.domain.use_case

import com.msieff.movies.domain.model.Video
import com.msieff.movies.domain.model.common.State
import com.msieff.movies.domain.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetMovieVideos
@Inject constructor(
    private val movieRepository: MovieRepository,
) : UseCase<Flow<State<List<Video>>?>, GetMovieVideos.Params>() {

    data class Params(val movieId: Int)

    override suspend fun run(params: Params): Flow<State<List<Video>>?> {
        return flow {
            emit(State.Loading())
            emit(movieRepository.getMovieVideos(params.movieId))
        }.flowOn(Dispatchers.IO)
    }
}