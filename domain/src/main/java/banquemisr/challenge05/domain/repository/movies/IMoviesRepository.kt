package banquemisr.challenge05.domain.repository.movies

import banquemisr.challenge05.domain.dto.DataState
import banquemisr.challenge05.domain.dto.movies.MoviesDTO
import kotlinx.coroutines.flow.Flow

interface IMoviesRepository {
    suspend fun getPlayingMovies(page: Int): Flow<DataState<MoviesDTO>>
    suspend fun getPopularMovies(page: Int): Flow<DataState<MoviesDTO>>
    suspend fun getUpcomingMovies(page: Int): Flow<DataState<MoviesDTO>>
}