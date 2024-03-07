package banquemisr.challenge05.presentation.viewmodel

import banquemisr.challenge05.domain.dto.DataState
import banquemisr.challenge05.domain.dto.ErrorModel
import banquemisr.challenge05.domain.dto.ErrorType
import banquemisr.challenge05.domain.dto.movies.Movie
import banquemisr.challenge05.domain.dto.movies.MoviesDTO
import banquemisr.challenge05.domain.usecase.getplayingmovies.IGetPlayingMoviesUseCase
import banquemisr.challenge05.domain.usecase.getpopularmovies.IGetPopularMoviesUseCase
import banquemisr.challenge05.domain.usecase.getupcomingmovies.IGetUpcomingMoviesUseCase
import banquemisr.challenge05.presentation.base.LoadingType
import banquemisr.challenge05.presentation.screens.home.viewmodel.MoviesContract
import banquemisr.challenge05.presentation.screens.home.viewmodel.MoviesViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test


class MoviesViewModelShould {
    private lateinit var moviesViewModel: MoviesViewModel
    private val iGetPlayingMoviesUseCase = mockk<IGetPlayingMoviesUseCase>()
    private val iGetUpcomingMoviesUseCase= mockk<IGetUpcomingMoviesUseCase>()
    private val iGetPopularMoviesUseCase = mockk<IGetPopularMoviesUseCase>()
    private val testDispatcher = StandardTestDispatcher()
    private val scope = TestScope(testDispatcher)

    @Before
    fun setUP(){
        moviesViewModel = MoviesViewModel(
            iGetPlayingMoviesUseCase = iGetPlayingMoviesUseCase,
            iGetUpcomingMoviesUseCase = iGetUpcomingMoviesUseCase,
            iGetPopularMoviesUseCase = iGetPopularMoviesUseCase,
            coroutineDispatcher = testDispatcher,
        )

    }
    private suspend fun initTest(dataState: DataState<MoviesDTO>, canPaginate: Boolean = false) {
        coEvery { iGetPlayingMoviesUseCase.getPlayingMovies(1)} returns flow {
            emit(dataState)
        }
        coEvery { iGetUpcomingMoviesUseCase.getUpcomingMovies(1)} returns flow {
            emit(dataState)
        }
        coEvery {iGetPopularMoviesUseCase.getPopularMovies(1)} returns flow {
            emit(dataState)
        }
    }

    @Before
    fun changeMainToTestDispatcher() {
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun `call GetHomeDataEvent the GetPlayingMovies , GetUpcomingMovies and GetPopularMovies functions one time`() = scope.runTest {
        initTest(DataState.Success(MoviesDTO()))
        moviesViewModel.setEvent(MoviesContract.Event.GetHomeData(LoadingType.FullLoading))
        advanceUntilIdle()
        coVerify { iGetPlayingMoviesUseCase.getPlayingMovies(1)}
        coVerify { iGetUpcomingMoviesUseCase.getUpcomingMovies(1)}
        coVerify { iGetPopularMoviesUseCase.getPopularMovies(1)}
    }
    @Test
    fun `not paginate if playing movies is empty`() = scope.runTest {
        coEvery { iGetPlayingMoviesUseCase.getPlayingMovies(1)} returns flow {
            emit(DataState.Success(MoviesDTO(canPaginate = true, movies = arrayListOf())))
        }
        moviesViewModel.setEvent(MoviesContract.Event.GetPlayingMovies(LoadingType.FullLoading))

        val values = mutableListOf<MoviesContract.State>()
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            moviesViewModel.uiState.toList(values)
        }
        advanceUntilIdle()
        moviesViewModel.setEvent(MoviesContract.Event.GetPlayingMovies(LoadingType.PaginationLoading))
        advanceUntilIdle()
        assertEquals(LoadingType.None, values[0].playingMoviesState.loadingType)
        assertEquals(LoadingType.FullLoading, values[1].playingMoviesState.loadingType)
        assertEquals(LoadingType.None, values[2].playingMoviesState.loadingType)
        assertEquals(3, values.size)
    }
    @Test
    fun `not paginate if upcoming movies is empty`() = scope.runTest {
        coEvery { iGetUpcomingMoviesUseCase.getUpcomingMovies(1)} returns flow {
            emit(DataState.Success(MoviesDTO(canPaginate = true, movies = arrayListOf())))
        }
        moviesViewModel.setEvent(MoviesContract.Event.GetUpcomingMovies(LoadingType.FullLoading))

        val values = mutableListOf<MoviesContract.State>()
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            moviesViewModel.uiState.toList(values)
        }
        advanceUntilIdle()
        moviesViewModel.setEvent(MoviesContract.Event.GetUpcomingMovies(LoadingType.PaginationLoading))
        advanceUntilIdle()
        assertEquals(LoadingType.None, values[0].upcomingMoviesState.loadingType)
        assertEquals(LoadingType.FullLoading, values[1].upcomingMoviesState.loadingType)
        assertEquals(LoadingType.None, values[2].upcomingMoviesState.loadingType)
        assertEquals(3, values.size)
    }
    @Test
    fun `not paginate if popular movies is empty`() = scope.runTest {
        coEvery { iGetPopularMoviesUseCase.getPopularMovies(1)} returns flow {
            emit(DataState.Success(MoviesDTO(canPaginate = true, movies = arrayListOf())))
        }
        moviesViewModel.setEvent(MoviesContract.Event.GetPopularMovies(LoadingType.FullLoading))

        val values = mutableListOf<MoviesContract.State>()
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            moviesViewModel.uiState.toList(values)
        }
        advanceUntilIdle()
        moviesViewModel.setEvent(MoviesContract.Event.GetPopularMovies(LoadingType.PaginationLoading))
        advanceUntilIdle()
        assertEquals(LoadingType.None, values[0].popularMoviesState.loadingType)
        assertEquals(LoadingType.FullLoading, values[1].popularMoviesState.loadingType)
        assertEquals(LoadingType.None, values[2].popularMoviesState.loadingType)
        assertEquals(3, values.size)
    }
    @Test
    fun `set loading to paging loading in playing movies state in case of loadingType is Pagination`() = scope.runTest {
        coEvery { iGetPlayingMoviesUseCase.getPlayingMovies(1)} returns flow {
            emit(DataState.Success(MoviesDTO(canPaginate = true, movies = arrayListOf(Movie()))))
        }
        moviesViewModel.setEvent(MoviesContract.Event.GetPlayingMovies(LoadingType.FullLoading))

        moviesViewModel.setState {
            copy(
                playingMoviesState = playingMoviesState.copy(
                    canPaginate = false
                )
            )
        }
        val values = mutableListOf<MoviesContract.State>()
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            moviesViewModel.uiState.toList(values)
        }
        advanceUntilIdle()
        moviesViewModel.setEvent(MoviesContract.Event.GetPlayingMovies(LoadingType.PaginationLoading))
        advanceUntilIdle()
        println(values)
        assertEquals(LoadingType.None, values[0].playingMoviesState.loadingType)
        assertEquals(LoadingType.FullLoading, values[1].playingMoviesState.loadingType)
        assertEquals(LoadingType.None, values[2].playingMoviesState.loadingType)
        assertEquals(LoadingType.PaginationLoading, values[3].playingMoviesState.loadingType)
        assertEquals(LoadingType.None, values[4].playingMoviesState.loadingType)
        assertEquals(5, values.size)
    }
    @Test
    fun `set loading to paging loading in upcoming movies state in case of loadingType is Pagination`() = scope.runTest {
        coEvery { iGetUpcomingMoviesUseCase.getUpcomingMovies(1)} returns flow {
            emit(DataState.Success(MoviesDTO(canPaginate = true, movies = arrayListOf(Movie()))))
        }
        moviesViewModel.setEvent(MoviesContract.Event.GetUpcomingMovies(LoadingType.FullLoading))

        moviesViewModel.setState {
            copy(
                playingMoviesState = playingMoviesState.copy(
                    canPaginate = false
                )
            )
        }
        val values = mutableListOf<MoviesContract.State>()
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            moviesViewModel.uiState.toList(values)
        }
        advanceUntilIdle()
        moviesViewModel.setEvent(MoviesContract.Event.GetUpcomingMovies(LoadingType.PaginationLoading))
        advanceUntilIdle()
        println(values)
        assertEquals(LoadingType.None, values[0].upcomingMoviesState.loadingType)
        assertEquals(LoadingType.FullLoading, values[1].upcomingMoviesState.loadingType)
        assertEquals(LoadingType.None, values[2].upcomingMoviesState.loadingType)
        assertEquals(LoadingType.PaginationLoading, values[3].upcomingMoviesState.loadingType)
        assertEquals(LoadingType.None, values[4].upcomingMoviesState.loadingType)
        assertEquals(5, values.size)
    }
    @Test
    fun `set loading to paging loading in popular movies state in case of loadingType is Pagination`() = scope.runTest {
        coEvery { iGetPopularMoviesUseCase.getPopularMovies(1)} returns flow {
            emit(DataState.Success(MoviesDTO(canPaginate = true, movies = arrayListOf(Movie()))))
        }
        moviesViewModel.setEvent(MoviesContract.Event.GetPopularMovies(LoadingType.FullLoading))

        moviesViewModel.setState {
            copy(
                playingMoviesState = playingMoviesState.copy(
                    canPaginate = false
                )
            )
        }
        val values = mutableListOf<MoviesContract.State>()
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            moviesViewModel.uiState.toList(values)
        }
        advanceUntilIdle()
        moviesViewModel.setEvent(MoviesContract.Event.GetPopularMovies(LoadingType.PaginationLoading))
        advanceUntilIdle()
        println(values)
        assertEquals(LoadingType.None, values[0].popularMoviesState.loadingType)
        assertEquals(LoadingType.FullLoading, values[1].popularMoviesState.loadingType)
        assertEquals(LoadingType.None, values[2].popularMoviesState.loadingType)
        assertEquals(LoadingType.PaginationLoading, values[3].popularMoviesState.loadingType)
        assertEquals(LoadingType.None, values[4].popularMoviesState.loadingType)
        assertEquals(5, values.size)
    }

    @Test
    fun `set loading updating correctly in playing movies after API call state changes`() = scope.runTest {
        coEvery { iGetPlayingMoviesUseCase.getPlayingMovies(1)} returns flow {
            emit(DataState.Success(MoviesDTO()))
        }
        val values = mutableListOf<MoviesContract.State>()
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            moviesViewModel.uiState.toList(values)
        }
        moviesViewModel.setEvent(MoviesContract.Event.GetPlayingMovies(LoadingType.FullLoading))
        advanceUntilIdle()
        assertEquals(LoadingType.None, values[0].playingMoviesState.loadingType)
        assertEquals(LoadingType.FullLoading, values[1].playingMoviesState.loadingType)
        assertEquals(LoadingType.None, values[2].playingMoviesState.loadingType)
        assertEquals(3, values.size)
    }
    @Test
    fun `set loading updating correctly in upcoming movies after API call state changes`() = scope.runTest {
        coEvery { iGetUpcomingMoviesUseCase.getUpcomingMovies(1)} returns flow {
            emit(DataState.Success(MoviesDTO()))
        }
        val values = mutableListOf<MoviesContract.State>()
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            moviesViewModel.uiState.toList(values)
        }
        moviesViewModel.setEvent(MoviesContract.Event.GetUpcomingMovies(LoadingType.FullLoading))
        advanceUntilIdle()
        assertEquals(LoadingType.None, values[0].upcomingMoviesState.loadingType)
        assertEquals(LoadingType.FullLoading, values[1].upcomingMoviesState.loadingType)
        assertEquals(LoadingType.None, values[2].upcomingMoviesState.loadingType)
        assertEquals(3, values.size)
    }
    @Test
    fun `set loading updating correctly in popular movies after API call state changes`() = scope.runTest {
        coEvery { iGetPopularMoviesUseCase.getPopularMovies(1)} returns flow {
            emit(DataState.Success(MoviesDTO()))
        }
        val values = mutableListOf<MoviesContract.State>()
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            moviesViewModel.uiState.toList(values)
        }
        moviesViewModel.setEvent(MoviesContract.Event.GetPopularMovies(LoadingType.FullLoading))
        advanceUntilIdle()
        assertEquals(LoadingType.None, values[0].popularMoviesState.loadingType)
        assertEquals(LoadingType.FullLoading, values[1].popularMoviesState.loadingType)
        assertEquals(LoadingType.None, values[2].popularMoviesState.loadingType)
        assertEquals(3, values.size)
    }

    @Test
    fun `set MoviesDTO successfully in playing state of contract`() = scope.runTest {
        coEvery { iGetPlayingMoviesUseCase.getPlayingMovies(1)} returns flow {
            emit(DataState.Success(MoviesDTO()))
        }
        moviesViewModel.setEvent(MoviesContract.Event.GetPlayingMovies(LoadingType.FullLoading))

        advanceUntilIdle()
        val result = moviesViewModel.uiState.first()
        assertEquals(MoviesDTO(), result.playingMoviesState.moviesDTO)
    }
    @Test
    fun `set MoviesDTO successfully in upcoming state of contract`() = scope.runTest {
        coEvery { iGetUpcomingMoviesUseCase.getUpcomingMovies(1)} returns flow {
            emit(DataState.Success(MoviesDTO()))
        }
        moviesViewModel.setEvent(MoviesContract.Event.GetUpcomingMovies(LoadingType.FullLoading))

        advanceUntilIdle()
        val result = moviesViewModel.uiState.first()
        assertEquals(MoviesDTO(), result.upcomingMoviesState.moviesDTO)
    }
    @Test
    fun `set MoviesDTO successfully in popular state of contract`() = scope.runTest {
        coEvery { iGetPopularMoviesUseCase.getPopularMovies(1)} returns flow {
            emit(DataState.Success(MoviesDTO()))
        }
        moviesViewModel.setEvent(MoviesContract.Event.GetPopularMovies(LoadingType.FullLoading))

        advanceUntilIdle()
        val result = moviesViewModel.uiState.first()
        assertEquals(MoviesDTO(), result.popularMoviesState.moviesDTO)
    }

    @Test
    fun `set movies list successfully in playing movies state of correct`() = scope.runTest {
        coEvery { iGetPlayingMoviesUseCase.getPlayingMovies(1)} returns flow {
            emit(DataState.Success(MoviesDTO(movies = arrayListOf(Movie()))))
        }
        moviesViewModel.setEvent(MoviesContract.Event.GetPlayingMovies(LoadingType.FullLoading))
        advanceUntilIdle()
        val result = moviesViewModel.uiState.first()
        assertEquals(1, result.playingMoviesState.movies?.size)
    }

    @Test
    fun `set movies list successfully in upcoming movies state of correct`() = scope.runTest {
        coEvery { iGetUpcomingMoviesUseCase.getUpcomingMovies(1)} returns flow {
            emit(DataState.Success(MoviesDTO(movies = arrayListOf(Movie()))))
        }
        moviesViewModel.setEvent(MoviesContract.Event.GetUpcomingMovies(LoadingType.FullLoading))
        advanceUntilIdle()
        val result = moviesViewModel.uiState.first()
        assertEquals(1, result.upcomingMoviesState.movies?.size)
    }

    @Test
    fun `set movies list successfully in popular movies state of correct`() = scope.runTest {
        coEvery { iGetPopularMoviesUseCase.getPopularMovies(1)} returns flow {
            emit(DataState.Success(MoviesDTO(movies = arrayListOf(Movie()))))
        }
        moviesViewModel.setEvent(MoviesContract.Event.GetPopularMovies(LoadingType.FullLoading))
        advanceUntilIdle()
        val result = moviesViewModel.uiState.first()
        assertEquals(1, result.popularMoviesState.movies?.size)
    }

    @Test
    fun `set error type NoInternetConnection in case of lose internet connection happens`() = scope.runTest {
        initTest(
            DataState.Error<Nothing>(
                ErrorType.NoInternetConnection,
                ErrorModel.NoInternetConnection
            ) as DataState<MoviesDTO>
        )
        moviesViewModel.setEvent(MoviesContract.Event.GetHomeData(LoadingType.FullLoading))
        advanceUntilIdle()
        val result = moviesViewModel.uiState.first()
        assertEquals(ErrorType.NoInternetConnection, result.errorType)

    }

    @Test
    fun `set ErrorModel in playing movies state in case of some any happened`() = scope.runTest {
        coEvery { iGetPlayingMoviesUseCase.getPlayingMovies(1)} returns flow {
            emit( DataState.Error<Nothing>(
                ErrorType.GeneralError,
                ErrorModel.GeneralError(1,"error")
            ) as DataState<MoviesDTO>)
        }
        moviesViewModel.setEvent(MoviesContract.Event.GetPlayingMovies(LoadingType.FullLoading))
        advanceUntilIdle()
        val result = moviesViewModel.uiState.first()
        assertEquals(ErrorModel.GeneralError(1,"error"), result.playingMoviesState.errorModel)
    }

    @Test
    fun `set ErrorModel in upcoming movies state in case of some any happened`() = scope.runTest {
        coEvery { iGetUpcomingMoviesUseCase.getUpcomingMovies(1)} returns flow {
            emit( DataState.Error<Nothing>(
                ErrorType.GeneralError,
                ErrorModel.GeneralError(1,"error")
            ) as DataState<MoviesDTO>)
        }
        moviesViewModel.setEvent(MoviesContract.Event.GetUpcomingMovies(LoadingType.FullLoading))
        advanceUntilIdle()
        val result = moviesViewModel.uiState.first()
        assertEquals(ErrorModel.GeneralError(1,"error"), result.upcomingMoviesState.errorModel)
    }
    @Test
    fun `set ErrorModel in popular movies state in case of some any happened`() = scope.runTest {
        coEvery { iGetPopularMoviesUseCase.getPopularMovies(1)} returns flow {
            emit( DataState.Error<Nothing>(
                ErrorType.GeneralError,
                ErrorModel.GeneralError(1,"error")
            ) as DataState<MoviesDTO>)
        }
        moviesViewModel.setEvent(MoviesContract.Event.GetPopularMovies(LoadingType.FullLoading))
        advanceUntilIdle()
        val result = moviesViewModel.uiState.first()
        assertEquals(ErrorModel.GeneralError(1,"error"), result.popularMoviesState.errorModel)
    }
    @Test
    fun `set ErrorModel in playing movies state in case of some any happened and return another list successfully`() = scope.runTest {
        coEvery { iGetPlayingMoviesUseCase.getPlayingMovies(1)} returns flow {
            emit( DataState.Error<Nothing>(
                ErrorType.GeneralError,
                ErrorModel.GeneralError(1,"error")
            ) as DataState<MoviesDTO>)
        }
        coEvery { iGetUpcomingMoviesUseCase.getUpcomingMovies(1)} returns flow {
            emit(DataState.Success(MoviesDTO(canPaginate = true, movies = arrayListOf(Movie()))))
        }
        coEvery { iGetPopularMoviesUseCase.getPopularMovies(1)} returns flow {
            emit(DataState.Success(MoviesDTO(canPaginate = true, movies = arrayListOf(Movie()))))
        }
        moviesViewModel.setEvent(MoviesContract.Event.GetHomeData(LoadingType.FullLoading))
        advanceUntilIdle()
        val result = moviesViewModel.uiState.value
        assertEquals(ErrorModel.GeneralError(1,"error"), result.playingMoviesState.errorModel)
        assertEquals(1, result.upcomingMoviesState.movies?.size)
        assertEquals(1, result.popularMoviesState.movies?.size)
    }

    @Test
    fun `append new movies to loaded playing movies list in case of pagination`() = scope.runTest {
        coEvery { iGetPlayingMoviesUseCase.getPlayingMovies(1)} returns flow {
            emit(DataState.Success(MoviesDTO(canPaginate = true, movies = arrayListOf(Movie()))))
        }
        moviesViewModel.setEvent(MoviesContract.Event.GetPlayingMovies(LoadingType.FullLoading))
        advanceUntilIdle()
        moviesViewModel.setEvent(MoviesContract.Event.GetPlayingMovies(LoadingType.PaginationLoading))
        advanceUntilIdle()
        val result = moviesViewModel.uiState.first()
        assertEquals(2, result.playingMoviesState.movies?.size)
    }
    @Test
    fun `append new movies to loaded upcoming movies list in case of pagination`() = scope.runTest {
        coEvery { iGetUpcomingMoviesUseCase.getUpcomingMovies(1)} returns flow {
            emit(DataState.Success(MoviesDTO(canPaginate = true, movies = arrayListOf(Movie()))))
        }
        moviesViewModel.setEvent(MoviesContract.Event.GetUpcomingMovies(LoadingType.FullLoading))
        advanceUntilIdle()
        moviesViewModel.setEvent(MoviesContract.Event.GetUpcomingMovies(LoadingType.PaginationLoading))
        advanceUntilIdle()
        val result = moviesViewModel.uiState.first()
        assertEquals(2, result.upcomingMoviesState.movies?.size)
    }
    @Test
    fun `append new movies to loaded popular movies list in case of pagination`() = scope.runTest {
        coEvery { iGetPopularMoviesUseCase.getPopularMovies(1)} returns flow {
            emit(DataState.Success(MoviesDTO(canPaginate = true, movies = arrayListOf(Movie()))))
        }
        moviesViewModel.setEvent(MoviesContract.Event.GetPopularMovies(LoadingType.FullLoading))
        advanceUntilIdle()
        moviesViewModel.setEvent(MoviesContract.Event.GetPopularMovies(LoadingType.PaginationLoading))
        advanceUntilIdle()
        val result = moviesViewModel.uiState.first()
        assertEquals(2, result.popularMoviesState.movies?.size)
    }
}