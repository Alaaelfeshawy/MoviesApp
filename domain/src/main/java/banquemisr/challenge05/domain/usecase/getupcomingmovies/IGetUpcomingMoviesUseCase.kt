package banquemisr.challenge05.domain.usecase.getupcomingmovies

import banquemisr.challenge05.domain.dto.DataState
import banquemisr.challenge05.domain.dto.movies.MoviesDTO
import kotlinx.coroutines.flow.Flow

interface IGetUpcomingMoviesUseCase {
    suspend fun getUpcomingMovies(page: Int): Flow<DataState<MoviesDTO>>
}