package banquemisr.challenge05.domain.usecase.getplayingmovies

import banquemisr.challenge05.domain.dto.DataState
import banquemisr.challenge05.domain.dto.movies.MoviesDTO
import kotlinx.coroutines.flow.Flow

interface IGetPlayingMoviesUseCase {
    suspend fun getPlayingMovies(page: Int): Flow<DataState<MoviesDTO>>
}