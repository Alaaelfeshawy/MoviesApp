package banquemisr.challenge05.domain.usecase.getmoviedetails

import banquemisr.challenge05.domain.dto.DataState
import banquemisr.challenge05.domain.dto.moviedetails.MovieDetailsDTO
import kotlinx.coroutines.flow.Flow

interface IGetMovieDetailsUseCase {

    suspend fun getMovieDetails(id: String): Flow<DataState<MovieDetailsDTO>>
}