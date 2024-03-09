package banquemisr.challenge05.domain.usecase.moviedetail

import banquemisr.challenge05.domain.dto.DataState
import banquemisr.challenge05.domain.dto.ErrorModel
import banquemisr.challenge05.domain.dto.ErrorType
import banquemisr.challenge05.domain.dto.moviedetails.MovieDetailsDTO
import banquemisr.challenge05.domain.repository.details.IMovieDetailsRepository
import banquemisr.challenge05.domain.usecase.getmoviedetails.GetMovieDetailsUseCase
import banquemisr.challenge05.domain.usecase.getmoviedetails.IGetMovieDetailsUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Test


class MovieDetailsUseCaseShould {

    private lateinit var iGetMovieDetailsUseCase : IGetMovieDetailsUseCase
    private val iMoviesRepository = mockk<IMovieDetailsRepository>()

    private suspend fun initTests(
        movieDetailsDataState: DataState<MovieDetailsDTO>,
    ) {
        coEvery {iMoviesRepository.getMovieDetails("1")} returns (flow {
            emit(movieDetailsDataState)
        })

        iGetMovieDetailsUseCase = GetMovieDetailsUseCase(iMoviesRepository)

    }

    @Test
    fun `call getMovieDetails one time`() = runTest {
        initTests(DataState.Success(MovieDetailsDTO()))
        iGetMovieDetailsUseCase.getMovieDetails("1")
        coVerify{iMoviesRepository.getMovieDetails("1")}
    }

    @Test
    fun `return success in case of DataState is Success`() = runTest {
        initTests(DataState.Success(MovieDetailsDTO()))
        val response = iGetMovieDetailsUseCase.getMovieDetails("1").first()
        assertEquals(response, DataState.Success(MovieDetailsDTO()))
    }

    @Test
    fun `return error in case of any error happens`() = runTest {
        val error = DataState.Error<Nothing>(ErrorType.GeneralError, ErrorModel.GeneralError(1, ""))
        initTests(error)
        val response = iGetMovieDetailsUseCase.getMovieDetails("1").first()
        assertEquals(response, error as DataState<*>)
    }

    @Test
    fun `return no internetConnection in case of there is no internet`() = runTest {
        val error = DataState.Error<Nothing>(ErrorType.NoInternetConnection, ErrorModel.NoInternetConnection)
        initTests(error)
        val response = iGetMovieDetailsUseCase.getMovieDetails("1").first()
        assertEquals(response, error as DataState<*>)

    }


}