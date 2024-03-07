package banquemisr.challenge05.data.remote.api

import banquemisr.challenge05.data.Constants
import banquemisr.challenge05.data.Constants.EndPoints.MOVIE_DETAILS
import banquemisr.challenge05.data.Constants.EndPoints.NOW_PLAYING
import banquemisr.challenge05.data.Constants.EndPoints.POPULAR
import banquemisr.challenge05.data.Constants.EndPoints.UPCOMING
import banquemisr.challenge05.data.Constants.QUERIES.PAGE
import banquemisr.challenge05.data.remote.response.moviedetails.MovieDetailsResponse
import banquemisr.challenge05.data.remote.response.movies.MoviesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface API {
    @GET(NOW_PLAYING)
    suspend fun getPlayingMovies(@Query(PAGE) page: Int): Response<MoviesResponse>
    @GET(POPULAR)
    suspend fun getPopularMovies(@Query(PAGE) page: Int): Response<MoviesResponse>
    @GET(UPCOMING)
    suspend fun getUpcomingMovies(@Query(PAGE) page: Int): Response<MoviesResponse>

    @GET(MOVIE_DETAILS)
    suspend fun getMovieDetails(
        @Path(Constants.Paths.MOVIE_ID)
        id: String
    ): Response<MovieDetailsResponse>

}