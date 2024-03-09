package banquemisr.challenge05.presentation.screens.details.viewmodel

import androidx.lifecycle.SavedStateHandle
import banquemisr.challenge05.domain.Constants
import banquemisr.challenge05.domain.R
import banquemisr.challenge05.domain.di.dispatchers.qualifiers.IODispatcher
import banquemisr.challenge05.domain.dto.DataState
import banquemisr.challenge05.domain.dto.ErrorModel
import banquemisr.challenge05.domain.dto.ErrorType
import banquemisr.challenge05.domain.dto.moviedetails.MovieDetailsDTO
import banquemisr.challenge05.domain.usecase.getmoviedetails.IGetMovieDetailsUseCase
import banquemisr.challenge05.presentation.base.BaseViewModel
import banquemisr.challenge05.presentation.base.LoadingType
import banquemisr.challenge05.presentation.base.Routes
import banquemisr.challenge05.presentation.base.UIEvent
import banquemisr.challenge05.presentation.screens.details.viewmodel.MovieDetailsViewModelContract.Event
import banquemisr.challenge05.presentation.screens.details.viewmodel.MovieDetailsViewModelContract.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val iMovieDetailsUseCase: IGetMovieDetailsUseCase,
    @IODispatcher private val coroutineDispatcher: CoroutineDispatcher,
) : BaseViewModel<Event, State>(
    coroutineDispatcher
) {

    init {
        setEvent(Event.GetMovieDetails)
    }

    override fun createInitialState(): State {
        return State()
    }

    override fun handleEvent(uiEvent: UIEvent) {
        when (uiEvent) {
            Event.GetMovieDetails -> callGetMoviesDetails()
        }
    }

    private fun callGetMoviesDetails() {
        val id = savedStateHandle.get<String>(Routes.Paths.MOVIE_DETAILS_ID)
        if (id == null)
            setState {
                copy(
                    loadingType = LoadingType.None,
                    errorModel = ErrorModel.GeneralError(
                        Constants.ErrorCodes.GENERAL_ERROR_CODE,
                        R.string.something_went_wrong
                    ),
                    errorType = ErrorType.GeneralError
                )
            }
        else
            getMoviesDetails(id)
    }

    private fun getMoviesDetails(id: String) {
        launchCoroutineScope {
            setState {
                copy(
                    loadingType = LoadingType.FullLoading,
                    id = id
                )
            }
            iMovieDetailsUseCase.getMovieDetails(id).collect { dataState ->
                when (dataState) {
                    is DataState.Success<MovieDetailsDTO> -> {
                        setState {
                            copy(
                                loadingType = LoadingType.None,
                                movieDetailsDTO = dataState.date,
                            )
                        }
                    }

                    is DataState.Error<*> -> {
                        setState {
                            copy(
                                loadingType = LoadingType.None,
                                errorType = dataState.errorType,
                                errorModel = dataState.errorModel
                            )
                        }
                    }
                }
            }
        }
    }
}