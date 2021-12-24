package com.msieff.movies.domain.use_case

import com.msieff.movies.domain.model.Movie
import com.msieff.movies.domain.model.common.State
import com.msieff.movies.domain.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class SearchMovies
@Inject constructor(
    private val movieRepository: MovieRepository,
) : UseCase<Flow<State<List<Movie>>?>, SearchMovies.Params>() {

    data class Params(val searchTerm: String)

    override suspend fun run(params: Params): Flow<State<List<Movie>>?> {
        return flow {
            emit(State.Loading())
            emit(movieRepository.search(params.searchTerm))
        }.flowOn(Dispatchers.IO)
    }
}