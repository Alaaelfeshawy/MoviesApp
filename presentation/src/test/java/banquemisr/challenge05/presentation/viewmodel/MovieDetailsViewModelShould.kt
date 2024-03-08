package banquemisr.challenge05.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import banquemisr.challenge05.domain.dto.DataState
import banquemisr.challenge05.domain.dto.ErrorModel
import banquemisr.challenge05.domain.dto.ErrorType
import banquemisr.challenge05.domain.dto.moviedetails.MovieDetailsDTO
import banquemisr.challenge05.domain.usecase.getmoviedetails.IGetMovieDetailsUseCase
import banquemisr.challenge05.presentation.base.LoadingType
import banquemisr.challenge05.presentation.base.Routes
import banquemisr.challenge05.presentation.screens.details.viewmodel.MovieDetailsViewModel
import banquemisr.challenge05.presentation.screens.details.viewmodel.MovieDetailsViewModelContract
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
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


class MovieDetailsViewModelShould {
    private lateinit var movieDetailsViewModel: MovieDetailsViewModel
    private val iGetMovieDetailsUseCase = mockk<IGetMovieDetailsUseCase>()
    private val savedStateHandle = mockk<SavedStateHandle>()
    private val testDispatcher = StandardTestDispatcher()
    private val scope = TestScope(testDispatcher)

    @Before
    fun setUP(){
        movieDetailsViewModel = MovieDetailsViewModel(
            iMovieDetailsUseCase = iGetMovieDetailsUseCase,
            savedStateHandle = savedStateHandle ,
            coroutineDispatcher = testDispatcher,
        )

    }
    private suspend fun initTest(dataState: DataState<MovieDetailsDTO> , id : String = "1") {
        every {savedStateHandle.get<String>(Routes.Paths.MOVIE_DETAILS_ID)} returns (id)

        coEvery { iGetMovieDetailsUseCase.getMovieDetails("1")} returns flow {
            emit(dataState)
        }
    }

    @Before
    fun changeMainToTestDispatcher() {
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun `call GetMovieDetails one time`() = scope.runTest {
        initTest(DataState.Success(MovieDetailsDTO()) )
        movieDetailsViewModel.setEvent(MovieDetailsViewModelContract.Event.GetMovieDetails)
        advanceUntilIdle()
        coVerify(exactly = 1) { iGetMovieDetailsUseCase.getMovieDetails("1")}
    }

    @Test
    fun `set loading updating correctly in  movie details after API call state changes`() = scope.runTest {
        initTest(DataState.Success(MovieDetailsDTO()))
        movieDetailsViewModel.setEvent(MovieDetailsViewModelContract.Event.GetMovieDetails)
        val values = mutableListOf<MovieDetailsViewModelContract.State>()
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            movieDetailsViewModel.uiState.toList(values)
        }
        advanceUntilIdle()
        assertEquals(LoadingType.None, values[0].loadingType)
        assertEquals(LoadingType.FullLoading, values[1].loadingType)
        assertEquals(LoadingType.None, values[2].loadingType)
        assertEquals(3, values.size)
    }

    @Test
    fun `set MovieDetailsDTO successfully in state of contract`() = scope.runTest {
        initTest(DataState.Success(MovieDetailsDTO()))
        movieDetailsViewModel.setEvent(MovieDetailsViewModelContract.Event.GetMovieDetails)
        advanceUntilIdle()
        val result = movieDetailsViewModel.uiState.first()
        assertEquals(MovieDetailsDTO(), result.movieDetailsDTO)
    }

    @Test
    fun `set error type NoInternetConnection in case of lose internet connection happens`() = scope.runTest {
        initTest(
            DataState.Error<Nothing>(
                ErrorType.NoInternetConnection,
                ErrorModel.NoInternetConnection
            )
        )
        movieDetailsViewModel.setEvent(MovieDetailsViewModelContract.Event.GetMovieDetails)
        advanceUntilIdle()
        val result = movieDetailsViewModel.uiState.first()
        assertEquals(ErrorType.NoInternetConnection, result.errorType)

    }

    @Test
    fun `set ErrorModel in movie details state in case of some any happened`() = scope.runTest {
        initTest( DataState.Error<Nothing>(
                ErrorType.GeneralError,
                ErrorModel.GeneralError(1,"error")
            ))
        movieDetailsViewModel.setEvent(MovieDetailsViewModelContract.Event.GetMovieDetails)
        advanceUntilIdle()
        val result = movieDetailsViewModel.uiState.first()
        assertEquals(ErrorModel.GeneralError(1,"error"), result.errorModel)
    }
}