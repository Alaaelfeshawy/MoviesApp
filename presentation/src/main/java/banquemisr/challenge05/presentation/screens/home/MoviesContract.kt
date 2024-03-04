package banquemisr.challenge05.presentation.screens.home

import banquemisr.challenge05.domain.dto.ErrorModel
import banquemisr.challenge05.domain.dto.ErrorType
import banquemisr.challenge05.domain.dto.movies.Movie
import banquemisr.challenge05.domain.dto.movies.MoviesDTO
import banquemisr.challenge05.presentation.base.LoadingType
import banquemisr.challenge05.presentation.base.UIEffect
import banquemisr.challenge05.presentation.base.UIEvent
import banquemisr.challenge05.presentation.base.UIState

class MoviesContract {
    data class State(
        val loadingType: LoadingType = LoadingType.None,
        val moviesDTO: MoviesDTO? = null,
        val errorModel: ErrorModel? = null,
        val errorType: ErrorType? = null,
        val movies: ArrayList<Movie>? = arrayListOf(),
        val isRefreshing: Boolean = false,
        val canPaginate: Boolean = false,
        val detailsRoute: String? = null,
    ) : UIState

    sealed class Event : UIEvent {
        data class GetPlayingMovies(val loadingType: LoadingType) : Event()
        data class OpenMovieDetails(val route: String) : Event()
    }

    sealed class Effect : UIEffect
}