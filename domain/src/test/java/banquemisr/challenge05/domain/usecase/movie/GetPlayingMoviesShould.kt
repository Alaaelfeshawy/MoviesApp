package banquemisr.challenge05.domain.usecase.movie

import banquemisr.challenge05.domain.dto.DataState
import banquemisr.challenge05.domain.dto.ErrorModel
import banquemisr.challenge05.domain.dto.ErrorType
import banquemisr.challenge05.domain.dto.movies.MoviesDTO
import banquemisr.challenge05.domain.repository.movies.IMoviesRepository
import banquemisr.challenge05.domain.usecase.getplayingmovies.GetPlayingMoviesUseCase
import banquemisr.challenge05.domain.usecase.getplayingmovies.IGetPlayingMoviesUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Test

class GetPlayingMoviesShould {

    private lateinit var iGetPlayingMoviesUseCase: IGetPlayingMoviesUseCase
    private val iMoviesRepository = mockk<IMoviesRepository>()

    private suspend fun initTests(dataState: DataState<MoviesDTO>) {
        coEvery {  iMoviesRepository.getPlayingMovies(1)} returns flow {
            emit(dataState)
        }
        iGetPlayingMoviesUseCase = GetPlayingMoviesUseCase(iMoviesRepository)
    }

    @Test
    fun `call getPlayingMovies one time`() = runTest {
        initTests(DataState.Success(MoviesDTO()))
        iGetPlayingMoviesUseCase.getPlayingMovies(1)
        coVerify{iMoviesRepository.getPlayingMovies(1)}
    }

    @Test
    fun `return success in case of DataState is Success`() = runTest {
        initTests(DataState.Success(MoviesDTO()))
        val response = iGetPlayingMoviesUseCase.getPlayingMovies(1).first()
        assertEquals(response, DataState.Success(MoviesDTO()))
    }

    @Test
    fun `return error in case of any error happens`() = runTest {
        val error = DataState.Error<Nothing>(ErrorType.GeneralError, ErrorModel.GeneralError(1, ""))
        initTests(error as DataState<MoviesDTO>)
        val response = iGetPlayingMoviesUseCase.getPlayingMovies(1).first()
        assertEquals(response, error as DataState<*>)
    }

    @Test
    fun `return no internetConnection in case of there is no internet`() = runTest {
        val error = DataState.Error<Nothing>(ErrorType.NoInternetConnection, ErrorModel.NoInternetConnection)
        initTests(error as DataState<MoviesDTO>)
        val response = iGetPlayingMoviesUseCase.getPlayingMovies(1).first()
        assertEquals(response, error as DataState<*>)

    }
}