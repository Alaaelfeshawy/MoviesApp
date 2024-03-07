package banquemisr.challenge05.data.repository

import banquemisr.challenge05.domain.dto.DataState
import banquemisr.challenge05.domain.dto.movies.MoviesDTO
import banquemisr.challenge05.data.remote.datasource.IRemoteDataSource
import banquemisr.challenge05.data.remote.response.movies.mapToMovies
import banquemisr.challenge05.data.util.mapToFlowDataState
import banquemisr.challenge05.domain.repository.movies.IMoviesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
class MoviesRepository @Inject constructor(private val iRemoteDataSource: IRemoteDataSource) :
    IMoviesRepository {

    override suspend fun getPlayingMovies(page: Int): Flow<DataState<MoviesDTO>> {
        return iRemoteDataSource.getPlayingMovies(page).mapToFlowDataState { moviesResponse ->
            val movies = if (moviesResponse?.movies != null) (moviesResponse.movies.mapToMovies()) as MutableList else mutableListOf()
            MoviesDTO(
                moviesResponse?.page,
                moviesResponse?.totalPages,
                movies
            )
        }
    }

    override suspend fun getPopularMovies(page: Int): Flow<DataState<MoviesDTO>> {
        return iRemoteDataSource.getPopularMovies(page).mapToFlowDataState { moviesResponse ->
            val movies = if (moviesResponse?.movies != null) (moviesResponse.movies.mapToMovies()) as MutableList else mutableListOf()
            MoviesDTO(
                moviesResponse?.page,
                moviesResponse?.totalPages,
                movies
            )
        }
    }

    override suspend fun getUpcomingMovies(page: Int): Flow<DataState<MoviesDTO>> {
        return iRemoteDataSource.getUpcomingMovies(page).mapToFlowDataState { moviesResponse ->
            val movies = if (moviesResponse?.movies != null) (moviesResponse.movies.mapToMovies()) as MutableList else mutableListOf()
            MoviesDTO(
                moviesResponse?.page,
                moviesResponse?.totalPages,
                movies
            )
        }
    }
}