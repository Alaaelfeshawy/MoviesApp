package banquemisr.challenge05.domain.usecase.getplayingmovies

import banquemisr.challenge05.domain.dto.DataState
import banquemisr.challenge05.domain.dto.movies.MoviesDTO
import banquemisr.challenge05.domain.repository.movies.IMoviesRepository
import banquemisr.challenge05.domain.usecase.getupcomingmovies.IGetUpcomingMoviesUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPlayingMoviesUseCase @Inject constructor(
    private val iMoviesRepository: IMoviesRepository
) : IGetPlayingMoviesUseCase {
    override suspend fun getPlayingMovies(page: Int): Flow<DataState<MoviesDTO>> {
        return iMoviesRepository.getPlayingMovies(page)
    }
}