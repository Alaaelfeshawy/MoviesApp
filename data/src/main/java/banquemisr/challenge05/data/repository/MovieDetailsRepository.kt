package banquemisr.challenge05.data.repository

import banquemisr.challenge05.domain.dto.DataState
import banquemisr.challenge05.data.remote.datasource.IRemoteDataSource
import banquemisr.challenge05.data.util.mapToFlowDataState
import banquemisr.challenge05.domain.dto.moviedetails.MovieDetailsDTO
import banquemisr.challenge05.domain.repository.details.IMovieDetailsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
class MovieDetailsRepository @Inject constructor(private val iRemoteDataSource: IRemoteDataSource) : IMovieDetailsRepository {

    override suspend fun getMovieDetails(id: String): Flow<DataState<MovieDetailsDTO>> {
        return iRemoteDataSource.getMoviesDetails(id).mapToFlowDataState { movieDetailsResponse ->
            MovieDetailsDTO(
                isAdult = movieDetailsResponse?.adult,
                backdropPath = movieDetailsResponse?.backdropPath,
                genres = movieDetailsResponse?.genres?.map {
                    it?.name
                },
                id = movieDetailsResponse?.id,
                language = movieDetailsResponse?.originalLanguage,
                title = movieDetailsResponse?.originalTitle,
                voteAverage = movieDetailsResponse?.voteAverage,
                languages = movieDetailsResponse?.spokenLanguages?.map {
                    it?.name
                },
               runTime =  movieDetailsResponse?.runtime,
               status =  movieDetailsResponse?.status ,
               overView = movieDetailsResponse?.overview,
            )
        }
    }

}