package com.msieff.movies.domain.use_case

import com.msieff.movies.domain.model.Category
import com.msieff.movies.domain.model.ContentCategory
import com.msieff.movies.domain.model.common.State
import com.msieff.movies.domain.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class DiscoverMoviesUseCase
@Inject constructor(
    private val moviesRepository: MovieRepository,
) : UseCase<Flow<State<List<ContentCategory>>?>, UseCase.None>() {

    override suspend fun run(params: None): Flow<State<List<ContentCategory>>> {
        return flow {
            emit(State.Loading())

            val result = mutableListOf<ContentCategory>()
            val popular = moviesRepository.fetchPopularMovies()
            val topRated = moviesRepository.fetchTopRatedMovies()

            if (popular is State.Data) {
                popular.data?.let {
                    if (it.isNotEmpty())
                        result.add(ContentCategory(
                            Category.POPULAR,
                            it
                        ))
                }
            }

            if (topRated is State.Data) {
                topRated.data?.let {
                    if (it.isNotEmpty())
                        result.add(ContentCategory(
                            Category.TOP_RATED,
                            it
                        ))
                }
            }
            emit(State.Data(result))
        }.flowOn(Dispatchers.IO)
    }
}