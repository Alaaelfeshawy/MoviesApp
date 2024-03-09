package banquemisr.challenge05.data.remote.datasource

import banquemisr.challenge05.data.remote.response.moviedetails.MovieDetailsResponse
import banquemisr.challenge05.data.remote.response.movies.MoviesResponse
import banquemisr.challenge05.data.remote.validate.APIResponseState


interface IRemoteDataSource {

    suspend fun getPlayingMovies(page: Int): APIResponseState<MoviesResponse>
    suspend fun getPopularMovies(page: Int): APIResponseState<MoviesResponse>
    suspend fun getUpcomingMovies(page: Int): APIResponseState<MoviesResponse>


    suspend fun getMoviesDetails(movieId: String): APIResponseState<MovieDetailsResponse>
}