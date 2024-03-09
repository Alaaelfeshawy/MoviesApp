package banquemisr.challenge05.domain.usecase.getmoviedetails

import banquemisr.challenge05.domain.dto.DataState
import banquemisr.challenge05.domain.dto.moviedetails.MovieDetailsDTO
import banquemisr.challenge05.domain.repository.details.IMovieDetailsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetMovieDetailsUseCase @Inject constructor(private val iMovieDetailsRepository: IMovieDetailsRepository) :
    IGetMovieDetailsUseCase {
    override suspend fun getMovieDetails(id: String): Flow<DataState<MovieDetailsDTO>> {
        return iMovieDetailsRepository.getMovieDetails(id)
    }
}