package banquemisr.challenge05.domain.usecase.getupcomingmovies

import banquemisr.challenge05.domain.dto.DataState
import banquemisr.challenge05.domain.dto.movies.MoviesDTO
import banquemisr.challenge05.domain.repository.movies.IMoviesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUpcomingMoviesUseCase @Inject constructor(
    private val iMoviesRepository: IMoviesRepository
) : IGetUpcomingMoviesUseCase {
    override suspend fun getUpcomingMovies(page: Int): Flow<DataState<MoviesDTO>> {
        return iMoviesRepository.getUpcomingMovies(page)
    }
}