package banquemisr.challenge05.presentation.screens.home.viewmodel

import banquemisr.challenge05.domain.dto.ErrorModel
import banquemisr.challenge05.domain.dto.ErrorType
import banquemisr.challenge05.domain.dto.movies.Movie
import banquemisr.challenge05.domain.dto.movies.MoviesDTO
import banquemisr.challenge05.presentation.base.LoadingType
import banquemisr.challenge05.presentation.base.UIEvent
import banquemisr.challenge05.presentation.base.UIState

class MoviesContract {
    data class State(
        val playingMoviesState : HomeState = HomeState(),
        val upcomingMoviesState : HomeState = HomeState(),
        val popularMoviesState : HomeState = HomeState(),
        val errorType: ErrorType? = null,
       ) : UIState

    sealed class Event : UIEvent {
        data class GetHomeData(val loadingType: LoadingType) : Event()
        data class GetPlayingMovies(val loadingType: LoadingType) : Event()
        data class GetUpcomingMovies(val loadingType: LoadingType) : Event()
        data class GetPopularMovies(val loadingType: LoadingType) : Event()
        data class NavigateToMovieDetails(val route: String) : Event()
    }

}

data class HomeState(
    val loadingType: LoadingType = LoadingType.None,
    val moviesDTO: MoviesDTO? = null,
    val errorModel: ErrorModel? = null,
    val errorType: ErrorType? = null,
    val movies: ArrayList<Movie>? = arrayListOf(),
    val canPaginate: Boolean = false,
    val detailsRoute: String? = null,
)