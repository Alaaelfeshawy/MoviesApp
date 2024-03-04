package banquemisr.challenge05.domain.usecase.getpopularmovies

import banquemisr.challenge05.domain.dto.DataState
import banquemisr.challenge05.domain.dto.movies.MoviesDTO
import kotlinx.coroutines.flow.Flow

interface IGetPopularMoviesUseCase {
    suspend fun getPopularMovies(page: Int): Flow<DataState<MoviesDTO>>
}