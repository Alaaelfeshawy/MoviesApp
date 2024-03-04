package banquemisr.challenge05.presentation.screens.home

import banquemisr.challenge05.domain.Constants.ErrorCodes.GENERAL_ERROR_CODE
import banquemisr.challenge05.domain.di.dispatchers.qualifiers.IODispatcher
import banquemisr.challenge05.domain.dto.DataState
import banquemisr.challenge05.domain.dto.ErrorModel
import banquemisr.challenge05.domain.dto.ErrorType
import banquemisr.challenge05.domain.dto.movies.MoviesDTO
import banquemisr.challenge05.domain.usecase.getplayingmovies.IGetPlayingMoviesUseCase
import banquemisr.challenge05.presentation.base.BaseViewModel
import banquemisr.challenge05.presentation.base.LoadingType
import banquemisr.challenge05.presentation.base.LoadingType.FullLoading
import banquemisr.challenge05.presentation.base.LoadingType.None
import banquemisr.challenge05.presentation.base.LoadingType.PaginationLoading
import banquemisr.challenge05.presentation.base.LoadingType.SwipeRefreshLoading
import banquemisr.challenge05.presentation.base.UIEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val iGetPlayingMoviesUseCase: IGetPlayingMoviesUseCase,
    @IODispatcher private val coroutineDispatcher: CoroutineDispatcher
) : BaseViewModel<MoviesContract.Event, MoviesContract.State, MoviesContract.Effect>(coroutineDispatcher) {

    init {
        setEvent(MoviesContract.Event.GetPlayingMovies(FullLoading))
    }

    override fun createInitialState(): MoviesContract.State {
        return MoviesContract.State()
    }

    override fun onExceptionThrown(throwable: Throwable) {
        setState {
            copy(
                loadingType = None,
                errorModel = ErrorModel.GeneralError(
                    GENERAL_ERROR_CODE,
                    throwable.message
                )
            )
        }
    }

    override fun handleEvent(uiEvent: UIEvent) {
        when (uiEvent) {
            is MoviesContract.Event.GetPlayingMovies -> {
                val canPaginate = currentState.canPaginate
                val moviesNotNullOrEmpty = !currentState.movies.isNullOrEmpty()
                val isNotLoading = currentState.loadingType == None
                when (uiEvent.loadingType) {
                    PaginationLoading -> {
                        if (canPaginate && isNotLoading && moviesNotNullOrEmpty)
                            callPlayingMovies(PaginationLoading)
                    }

                    else -> callPlayingMovies(uiEvent.loadingType)
                }
            }

            is MoviesContract.Event.OpenMovieDetails -> {
                setState {
                    copy(detailsRoute = uiEvent.route)
                }
            }
        }
    }

    private fun getCurrentPage(loadingType: LoadingType): Int {
        val currentPage = currentState.moviesDTO?.currentPage ?: 0
        return if (loadingType == FullLoading || loadingType == SwipeRefreshLoading)
            1
        else
            currentPage + 1
    }

    private fun onDataStateError(
        errorModel: ErrorModel?,
        errorType: ErrorType?,
        loadingType: LoadingType
    ) {
        setState {
            copy(
                errorModel = if (loadingType != PaginationLoading) errorModel else null,
                errorType = errorType,
                loadingType = None,
                isRefreshing = false,
            )
        }
    }

    private fun onDataStateSuccess(
        loadingType: LoadingType,
        moviesDTO: MoviesDTO?
    ) {
        val isSwipeRefresh = loadingType == SwipeRefreshLoading
        val movies = currentState.movies?.apply {
            if (isSwipeRefresh)
                clear()
            addAll(
                moviesDTO?.movies ?: arrayListOf()
            )
        }
        setState {
            copy(
                movies = movies,
                moviesDTO = moviesDTO,
                loadingType = None,
                isRefreshing = false,
                canPaginate = moviesDTO?.canPaginate == true
            )
        }
    }

    private fun callPlayingMovies(loadingType: LoadingType = None) {
        launchCoroutineScope {
            setState {
                copy(
                    loadingType = loadingType,
                    isRefreshing = loadingType == SwipeRefreshLoading
                )
            }
            iGetPlayingMoviesUseCase.getPlayingMovies(getCurrentPage(loadingType))
                .collect { playingMoviesDataState ->
                    when (playingMoviesDataState) {
                        is DataState.Error<*> -> onDataStateError(
                            playingMoviesDataState.errorModel,
                            playingMoviesDataState.errorType,
                            loadingType
                        )

                        is DataState.Success -> onDataStateSuccess(
                            loadingType,
                            playingMoviesDataState.date
                        )
                    }
                }
        }
    }
}