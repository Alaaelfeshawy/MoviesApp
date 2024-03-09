package banquemisr.challenge05.data.repository.moviedetails

import banquemisr.challenge05.data.remote.datasource.IRemoteDataSource
import banquemisr.challenge05.data.remote.response.moviedetails.MovieDetailsResponse
import banquemisr.challenge05.data.remote.validate.APIResponseState
import banquemisr.challenge05.data.repository.MovieDetailsRepository
import banquemisr.challenge05.domain.dto.DataState
import banquemisr.challenge05.domain.dto.ErrorModel
import banquemisr.challenge05.domain.dto.ErrorType
import banquemisr.challenge05.domain.dto.moviedetails.MovieDetailsDTO
import banquemisr.challenge05.domain.repository.details.IMovieDetailsRepository
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Test

class MovieDetailsRepositoryShould {

    private lateinit var iMovieDetailsRepository: IMovieDetailsRepository
    private val iRemoteDataSource = mockk<IRemoteDataSource>()

    private suspend fun initTests(apiResponse: APIResponseState<MovieDetailsResponse>) {
        coEvery {  iRemoteDataSource.getMoviesDetails("1")} returns apiResponse
        iMovieDetailsRepository = MovieDetailsRepository(iRemoteDataSource)
    }

    @Test
    fun `call getMovieDetails one time`() = runTest {
        initTests(APIResponseState.ValidResponse(MovieDetailsResponse()))
        iMovieDetailsRepository.getMovieDetails("1")
        coEvery { iRemoteDataSource.getMoviesDetails("1") }
    }

    @Test
    fun `return success in case of API response is success`() = runTest {
        initTests(APIResponseState.ValidResponse(MovieDetailsResponse()))
        val result = iMovieDetailsRepository.getMovieDetails("1").first()
        assertEquals(DataState.Success(MovieDetailsDTO()) as DataState<*>, result)
    }

    @Test
    fun `return error in case of API response is not success`() = runTest {
        initTests(
            APIResponseState.NotValidResponse(
                1000,
                "error"
            ) as APIResponseState<MovieDetailsResponse>
        )
        val result = iMovieDetailsRepository.getMovieDetails("1").first()
        assertEquals(
            DataState.Error<Nothing>(
                ErrorType.GeneralError,
                ErrorModel.GeneralError(1000, "error")
            ), result
        )
    }

    @Test
    fun `return NoInternetConnection in case of no internet connection is returned`() = runTest {
        initTests(APIResponseState.NoInternetConnection as APIResponseState<MovieDetailsResponse>)
        val result = iMovieDetailsRepository.getMovieDetails("1").first()
        assertEquals(
            DataState.Error<Nothing>(ErrorType.NoInternetConnection, ErrorModel.NoInternetConnection),
            result
        )
    }
}