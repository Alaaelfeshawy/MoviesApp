package banquemisr.challenge05.domain.usecase.getpopularmovies

import banquemisr.challenge05.domain.dto.DataState
import banquemisr.challenge05.domain.dto.movies.MoviesDTO
import banquemisr.challenge05.domain.repository.movies.IMoviesRepository
import banquemisr.challenge05.domain.usecase.getplayingmovies.IGetPlayingMoviesUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPopularMoviesUseCase @Inject constructor(
    private val iMoviesRepository: IMoviesRepository
) : IGetPopularMoviesUseCase {
    override suspend fun getPopularMovies(page: Int): Flow<DataState<MoviesDTO>> {
        return iMoviesRepository.getPopularMovies(page)
    }
}