package banquemisr.challenge05.presentation.screens.home

import androidx.lifecycle.viewModelScope
import banquemisr.challenge05.domain.di.dispatchers.qualifiers.IODispatcher
import banquemisr.challenge05.domain.dto.DataState
import banquemisr.challenge05.domain.dto.ErrorModel
import banquemisr.challenge05.domain.dto.ErrorType
import banquemisr.challenge05.domain.dto.movies.MoviesDTO
import banquemisr.challenge05.domain.usecase.getplayingmovies.IGetPlayingMoviesUseCase
import banquemisr.challenge05.domain.usecase.getpopularmovies.IGetPopularMoviesUseCase
import banquemisr.challenge05.domain.usecase.getupcomingmovies.IGetUpcomingMoviesUseCase
import banquemisr.challenge05.presentation.base.BaseViewModel
import banquemisr.challenge05.presentation.base.LoadingType
import banquemisr.challenge05.presentation.base.LoadingType.FullLoading
import banquemisr.challenge05.presentation.base.LoadingType.None
import banquemisr.challenge05.presentation.base.LoadingType.PaginationLoading
import banquemisr.challenge05.presentation.base.LoadingType.SwipeRefreshLoading
import banquemisr.challenge05.presentation.base.UIEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val iGetPlayingMoviesUseCase: IGetPlayingMoviesUseCase,
    private val iGetUpcomingMoviesUseCase: IGetUpcomingMoviesUseCase,
    private val iGetPopularMoviesUseCase: IGetPopularMoviesUseCase,
    @IODispatcher private val coroutineDispatcher: CoroutineDispatcher
) : BaseViewModel<MoviesContract.Event, MoviesContract.State, MoviesContract.Effect>(coroutineDispatcher) {

    init {
        setEvent(MoviesContract.Event.GetHomeData(FullLoading))
    }
    override fun createInitialState(): MoviesContract.State {
        return MoviesContract.State()
    }

//    override fun onExceptionThrown(throwable: Throwable) {
//        setState {
//            copy(
//                loadingType = None,
//                errorModel = ErrorModel.GeneralError(
//                    GENERAL_ERROR_CODE,
//                    throwable.message
//                )
//            )
//        }
//    }

    override fun handleEvent(uiEvent: UIEvent) {
        when (uiEvent) {
              is MoviesContract.Event.GetHomeData ->{
                  viewModelScope.launch {
                      try {
                          callPlayingMovies(uiEvent.loadingType)
                      } catch (e: Exception) {
                          onPlayingMoviesError(
                              errorModel = ErrorModel.GeneralError(1, "something happened wrong , please try later",),
                              errorType = ErrorType.GeneralError,
                              loadingType = None
                          )
                      }
                     try {
                          callUpcomingMovies(uiEvent.loadingType)
                      } catch (e: Exception) {
                          onUpcomingMoviesError(
                              errorModel = ErrorModel.GeneralError(1, "something happened wrong , please try later",),
                              errorType = ErrorType.GeneralError,
                              loadingType = None
                          )
                      }
                      try {
                          callPopularMovies(uiEvent.loadingType)
                      } catch (e: Exception) {
                          onUpcomingMoviesError(
                              errorModel = ErrorModel.GeneralError(1, "something happened wrong",),
                              errorType = null,
                              loadingType = None
                          )
                      }
                  }
              }
            is MoviesContract.Event.GetPlayingMovies -> {
                getPlayingMovies(uiEvent)
            }
            is MoviesContract.Event.GetUpcomingMovies -> {
                getUpcomingMovies(uiEvent)
            }
            is MoviesContract.Event.GetPopularMovies -> {
                getPopularMovies(uiEvent)
            }

//            is MoviesContract.Event.NavigateToMovieDetails -> {
//
//            }
        }
    }

    private fun getUpcomingMovies(uiEvent: MoviesContract.Event.GetUpcomingMovies) {
        val canPaginate = currentState.upcomingMoviesState.canPaginate
        val moviesNotNullOrEmpty = !currentState.upcomingMoviesState.movies.isNullOrEmpty()
        val isNotLoading = currentState.upcomingMoviesState.loadingType == None
        when (uiEvent.loadingType) {
            PaginationLoading -> {
                if (canPaginate && isNotLoading && moviesNotNullOrEmpty)
                    callUpcomingMovies(PaginationLoading)
            }

            else -> callUpcomingMovies(uiEvent.loadingType)
        }
    }
    private fun getPlayingMovies(uiEvent: MoviesContract.Event.GetPlayingMovies) {
        val canPaginate = currentState.playingMoviesState.canPaginate
        val moviesNotNullOrEmpty = !currentState.playingMoviesState.movies.isNullOrEmpty()
        val isNotLoading = currentState.playingMoviesState.loadingType == None
        when (uiEvent.loadingType) {
            PaginationLoading -> {
                if (canPaginate && isNotLoading && moviesNotNullOrEmpty)
                    callPlayingMovies(PaginationLoading)
            }

            else -> callPlayingMovies(uiEvent.loadingType)
        }
    }
    private fun getPopularMovies(uiEvent: MoviesContract.Event.GetPopularMovies) {
        val canPaginate = currentState.popularMoviesState.canPaginate
        val moviesNotNullOrEmpty = !currentState.popularMoviesState.movies.isNullOrEmpty()
        val isNotLoading = currentState.popularMoviesState.loadingType == None
        when (uiEvent.loadingType) {
            PaginationLoading -> {
                if (canPaginate && isNotLoading && moviesNotNullOrEmpty)
                    callPopularMovies(PaginationLoading)
            }

            else -> callPopularMovies(uiEvent.loadingType)
        }
    }
    private fun getCurrentPageForPlayingMovies(loadingType: LoadingType): Int {
        val currentPage = currentState.playingMoviesState.moviesDTO?.currentPage ?: 0
        return if (loadingType == FullLoading || loadingType == SwipeRefreshLoading)
            1
        else
            currentPage + 1
    }
    private fun getCurrentPageForUpcomingMovies(loadingType: LoadingType): Int {
        val currentPage = currentState.upcomingMoviesState.moviesDTO?.currentPage ?: 0
        return if (loadingType == FullLoading || loadingType == SwipeRefreshLoading)
            1
        else
            currentPage + 1
    }
    private fun getCurrentPageForPopularMovies(loadingType: LoadingType): Int {
        val currentPage = currentState.popularMoviesState.moviesDTO?.currentPage ?: 0
        return if (loadingType == FullLoading || loadingType == SwipeRefreshLoading)
            1
        else
            currentPage + 1
    }

    private fun callPlayingMovies(loadingType: LoadingType = None) {
        launchCoroutineScope {
            setState {
                copy(
                    playingMoviesState = currentState.playingMoviesState.copy(
                        loadingType = loadingType,
                        isRefreshing = loadingType == SwipeRefreshLoading
                    ),
                )
            }
            iGetPlayingMoviesUseCase.getPlayingMovies(getCurrentPageForPlayingMovies(loadingType))
                .collect { playingMoviesDataState ->
                    when (playingMoviesDataState) {
                        is DataState.Error<*> -> onPlayingMoviesError(
                            playingMoviesDataState.errorModel,
                            playingMoviesDataState.errorType,
                            loadingType
                        )

                        is DataState.Success -> onPlayingMoviesSuccess(
                            loadingType,
                            playingMoviesDataState.date
                        )
                    }
                }
        }
    }

    private fun onPlayingMoviesError(
        errorModel: ErrorModel?,
        errorType: ErrorType?,
        loadingType: LoadingType
    ) {
        setState {
            copy(
                playingMoviesState = HomeState(
                    errorModel = if (loadingType != PaginationLoading) errorModel else null,
                    errorType = errorType,
                    loadingType = None,
                    isRefreshing = false,
                ),
                errorType = errorType,
            )
        }
    }

    private fun onPlayingMoviesSuccess(
        loadingType: LoadingType,
        moviesDTO: MoviesDTO?
    ) {
        val isSwipeRefresh = loadingType == SwipeRefreshLoading
        val movies = currentState.playingMoviesState.movies?.apply {
            if (isSwipeRefresh)
                clear()
            addAll(
                moviesDTO?.movies ?: arrayListOf()
            )
        }
        setState {
            copy(
                playingMoviesState = HomeState(
                    movies = movies,
                    moviesDTO = moviesDTO,
                    loadingType = None,
                    isRefreshing = false,
                    canPaginate = moviesDTO?.canPaginate == true
                ),
            )
        }
    }

    private fun callUpcomingMovies(loadingType: LoadingType = None) {
        launchCoroutineScope {
            setState {
                copy(
                    upcomingMoviesState = currentState.upcomingMoviesState.copy(
                        loadingType = loadingType,
                        isRefreshing = loadingType == SwipeRefreshLoading
                    ),
                )
            }
            iGetUpcomingMoviesUseCase.getUpcomingMovies(getCurrentPageForUpcomingMovies(loadingType))
                .collect { upcomingMoviesDataState ->
                    when (upcomingMoviesDataState) {
                        is DataState.Error<*> -> onUpcomingMoviesError(
                            upcomingMoviesDataState.errorModel,
                            upcomingMoviesDataState.errorType,
                            loadingType
                        )

                        is DataState.Success -> onUpcomingMoviesSuccess(
                            loadingType,
                            upcomingMoviesDataState.date
                        )
                    }
                }
        }
    }

    private fun onUpcomingMoviesError(
        errorModel: ErrorModel?,
        errorType: ErrorType?,
        loadingType: LoadingType
    ) {
        setState {
            copy(
                upcomingMoviesState = HomeState(
                    errorModel = if (loadingType != PaginationLoading) errorModel else null,
                    errorType = errorType,
                    loadingType = None,
                    isRefreshing = false,
                ),
                errorType = errorType,
                )
        }
    }

    private fun onUpcomingMoviesSuccess(
        loadingType: LoadingType,
        moviesDTO: MoviesDTO?
    ) {
        val isSwipeRefresh = loadingType == SwipeRefreshLoading
        val movies = currentState.upcomingMoviesState.movies?.apply {
            if (isSwipeRefresh)
                clear()
            addAll(
                moviesDTO?.movies ?: arrayListOf()
            )
        }
        setState {
            copy(
                upcomingMoviesState = HomeState(
                    movies = movies,
                    moviesDTO = moviesDTO,
                    loadingType = None,
                    isRefreshing = false,
                    canPaginate = moviesDTO?.canPaginate == true
                ),
            )
        }
    }

    private fun callPopularMovies(loadingType: LoadingType = None) {
        launchCoroutineScope {
            setState {
                copy(
                    popularMoviesState = currentState.popularMoviesState.copy(
                        loadingType = loadingType,
                        isRefreshing = loadingType == SwipeRefreshLoading
                    ),
                )
            }
            iGetPopularMoviesUseCase.getPopularMovies(getCurrentPageForPopularMovies(loadingType))
                .collect { popularMoviesDataState ->
                    when (popularMoviesDataState) {
                        is DataState.Error<*> -> onPopularMoviesError(
                            popularMoviesDataState.errorModel,
                            popularMoviesDataState.errorType,
                            loadingType
                        )

                        is DataState.Success -> onPopularMoviesSuccess(
                            loadingType,
                            popularMoviesDataState.date
                        )
                    }
                }
        }
    }

    private fun onPopularMoviesError(
        errorModel: ErrorModel?,
        errorType: ErrorType?,
        loadingType: LoadingType
    ) {
        setState {
            copy(
                popularMoviesState = HomeState(
                    errorModel = if (loadingType != PaginationLoading) errorModel else null,
                    errorType = errorType,
                    loadingType = None,
                    isRefreshing = false,
                ),
                errorType = errorType,

                )
        }
    }

    private fun onPopularMoviesSuccess(
        loadingType: LoadingType,
        moviesDTO: MoviesDTO?
    ) {
        val isSwipeRefresh = loadingType == SwipeRefreshLoading
        val movies = currentState.popularMoviesState.movies?.apply {
            if (isSwipeRefresh)
                clear()
            addAll(
                moviesDTO?.movies ?: arrayListOf()
            )
        }
        setState {
            copy(
                popularMoviesState = HomeState(
                    movies = movies,
                    moviesDTO = moviesDTO,
                    loadingType = None,
                    isRefreshing = false,
                    canPaginate = moviesDTO?.canPaginate == true
                ),
            )
        }
    }

}