package banquemisr.challenge05.presentation.screens.details.viewmodel

import banquemisr.challenge05.domain.dto.ErrorModel
import banquemisr.challenge05.domain.dto.ErrorType
import banquemisr.challenge05.domain.dto.moviedetails.MovieDetailsDTO
import banquemisr.challenge05.presentation.base.LoadingType
import banquemisr.challenge05.presentation.base.UIEvent
import banquemisr.challenge05.presentation.base.UIState

class MovieDetailsViewModelContract {
    data class State(
        val id: String? = null,
        val loadingType: LoadingType? = LoadingType.None,
        val movieDetailsDTO: MovieDetailsDTO? = null,
        val errorType: ErrorType? = null,
        val errorModel: ErrorModel? = null
    ) : UIState

    sealed class Event : UIEvent {
        object GetMovieDetails : Event()
    }
}