package banquemisr.challenge05.domain.repository.details

import banquemisr.challenge05.domain.dto.DataState
import banquemisr.challenge05.domain.dto.moviedetails.MovieDetailsDTO
import kotlinx.coroutines.flow.Flow

interface IMovieDetailsRepository {
    suspend fun getMovieDetails(id: String): Flow<DataState<MovieDetailsDTO>>
}