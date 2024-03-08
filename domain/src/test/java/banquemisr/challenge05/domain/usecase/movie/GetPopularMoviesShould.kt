package banquemisr.challenge05.domain.usecase.movie

import banquemisr.challenge05.domain.dto.DataState
import banquemisr.challenge05.domain.dto.ErrorModel
import banquemisr.challenge05.domain.dto.ErrorType
import banquemisr.challenge05.domain.dto.movies.MoviesDTO
import banquemisr.challenge05.domain.repository.movies.IMoviesRepository
import banquemisr.challenge05.domain.usecase.getpopularmovies.GetPopularMoviesUseCase
import banquemisr.challenge05.domain.usecase.getpopularmovies.IGetPopularMoviesUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Test

class GetPopularMoviesShould {

    private lateinit var iGetPopularMoviesUseCase: IGetPopularMoviesUseCase
    private val iMoviesRepository = mockk<IMoviesRepository>()

    private suspend fun initTests(dataState: DataState<MoviesDTO>) {
        coEvery {  iMoviesRepository.getPopularMovies(1)} returns(flow {
            emit(dataState)
        })
        iGetPopularMoviesUseCase = GetPopularMoviesUseCase(iMoviesRepository)
    }

    @Test
    fun `call getPopularMovies one time`() = runTest {
        initTests(DataState.Success(MoviesDTO()))
        iGetPopularMoviesUseCase.getPopularMovies(1)
        coVerify{iMoviesRepository.getPopularMovies(1)}
    }

    @Test
    fun `return success in case of DataState is Success`() = runTest {
        initTests(DataState.Success(MoviesDTO()))
        val response = iGetPopularMoviesUseCase.getPopularMovies(1).first()
        TestCase.assertEquals(response, DataState.Success(MoviesDTO()))
    }

    @Test
    fun `return error in case of any error happens`() = runTest {
        val error = DataState.Error<Nothing>(ErrorType.GeneralError, ErrorModel.GeneralError(1, ""))
        initTests(error as DataState<MoviesDTO>)
        val response = iGetPopularMoviesUseCase.getPopularMovies(1).first()
        TestCase.assertEquals(response, error as DataState<*>)
    }

    @Test
    fun `return no internetConnection in case of there is no internet`() = runTest {
        val error = DataState.Error<Nothing>(ErrorType.NoInternetConnection, ErrorModel.NoInternetConnection)
        initTests(error as DataState<MoviesDTO>)
        val response = iGetPopularMoviesUseCase.getPopularMovies(1).first()
        TestCase.assertEquals(response, error as DataState<*>)

    }
}