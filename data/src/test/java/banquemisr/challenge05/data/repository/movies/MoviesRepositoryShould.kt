package banquemisr.challenge05.data.repository.movies

import banquemisr.challenge05.data.remote.datasource.IRemoteDataSource
import banquemisr.challenge05.data.remote.response.movies.MoviesResponse
import banquemisr.challenge05.data.remote.validate.APIResponseState
import banquemisr.challenge05.data.repository.MoviesRepository
import banquemisr.challenge05.domain.dto.DataState
import banquemisr.challenge05.domain.dto.ErrorModel
import banquemisr.challenge05.domain.dto.ErrorType
import banquemisr.challenge05.domain.dto.movies.MoviesDTO
import banquemisr.challenge05.domain.repository.movies.IMoviesRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class MoviesRepositoryShould {

    private lateinit var moviesRepository: IMoviesRepository
    private val iRemoteDataSource = mockk<IRemoteDataSource>()

    @Before
    fun setUp(){
        moviesRepository = MoviesRepository(this.iRemoteDataSource)
    }

    // test playing no movies
    private suspend fun initTests(apiResponseState: APIResponseState<MoviesResponse>) {
        coEvery {  (iRemoteDataSource.getPlayingMovies(1))} returns (apiResponseState)
    }

    @Test
    fun `call getPlayingMovies one time`() = runTest {
        initTests(APIResponseState.ValidResponse(MoviesResponse()))
        moviesRepository.getPlayingMovies(1)
        coVerify { iRemoteDataSource.getPlayingMovies(1) }
    }

    @Test
    fun `return success state in case of APIResponseState is Valid`() = runTest {
        initTests(APIResponseState.ValidResponse(MoviesResponse()))
        val response = moviesRepository.getPlayingMovies(1).first()
        assertEquals(response, DataState.Success(MoviesDTO()))
    }

    @Test
    fun `return error state with noInternetConnection params in case of no internetConnection`() =
        runTest {
            initTests(APIResponseState.NoInternetConnection as APIResponseState<MoviesResponse>)
            val response = moviesRepository.getPlayingMovies(1).first()
            assertEquals(
                response,
                DataState.Error<Nothing>(ErrorType.NoInternetConnection, ErrorModel.NoInternetConnection)
                        as DataState<*>
            )
        }

    @Test
    fun `return error state with GeneralError params in case of some error happens`() = runTest {
        initTests(
            APIResponseState.NotValidResponse(
                1000,
                "something went wrong"
            ) as APIResponseState<MoviesResponse>
        )
        val response = moviesRepository.getPlayingMovies(1).first()
        assertEquals(
            response,
            DataState.Error<Nothing>(
                ErrorType.GeneralError,
                ErrorModel.GeneralError(1000, "something went wrong")
            ) as DataState<*>
        )
    }

    // test upcoming movies
    private suspend fun setupUpcomingMovies(apiResponseState: APIResponseState<MoviesResponse>) {
        coEvery {  (iRemoteDataSource.getUpcomingMovies(1))} returns (apiResponseState)
    }

    @Test
    fun `call getUpcomingMovies one time`() = runTest {
        setupUpcomingMovies(APIResponseState.ValidResponse(MoviesResponse()))
        moviesRepository.getUpcomingMovies(1)
        coVerify { iRemoteDataSource.getUpcomingMovies(1) }
    }

    @Test
    fun `call getUpcomingMovies and return success state in case of APIResponseState is Valid`() = runTest {
        setupUpcomingMovies(APIResponseState.ValidResponse(MoviesResponse()))
        val response = moviesRepository.getUpcomingMovies(1).first()
        assertEquals(response, DataState.Success(MoviesDTO()))
    }

    @Test
    fun `call getUpcomingMovies and return error state with noInternetConnection params in case of no internetConnection`() =
        runTest {
            setupUpcomingMovies(APIResponseState.NoInternetConnection as APIResponseState<MoviesResponse>)
            val response = moviesRepository.getUpcomingMovies(1).first()
            assertEquals(
                response,
                DataState.Error<Nothing>(ErrorType.NoInternetConnection, ErrorModel.NoInternetConnection)
                        as DataState<*>
            )
        }

    @Test
    fun `call getUpcomingMovies and  return error state with GeneralError params in case of some error happens`() = runTest {
        setupUpcomingMovies(
            APIResponseState.NotValidResponse(
                1000,
                "something went wrong"
            ) as APIResponseState<MoviesResponse>
        )
        val response = moviesRepository.getUpcomingMovies(1).first()
        assertEquals(
            response,
            DataState.Error<Nothing>(
                ErrorType.GeneralError,
                ErrorModel.GeneralError(1000, "something went wrong")
            ) as DataState<*>
        )
    }


    // test popular movies
    private suspend fun setupPopularMovies(apiResponseState: APIResponseState<MoviesResponse>) {
        coEvery {  (iRemoteDataSource.getPopularMovies(1))} returns (apiResponseState)
    }

    @Test
    fun `call getPopularMovies one time`() = runTest {
        setupPopularMovies(APIResponseState.ValidResponse(MoviesResponse()))
        moviesRepository.getPopularMovies(1)
        coVerify { iRemoteDataSource.getPopularMovies(1) }
    }

    @Test
    fun `call getPopularMovies and return success state in case of APIResponseState is Valid`() = runTest {
        setupPopularMovies(APIResponseState.ValidResponse(MoviesResponse()))
        val response = moviesRepository.getPopularMovies(1).first()
        assertEquals(response, DataState.Success(MoviesDTO()))
    }

    @Test
    fun `call getPopularMovies and return error state with noInternetConnection params in case of no internetConnection`() =
        runTest {
            setupPopularMovies(APIResponseState.NoInternetConnection as APIResponseState<MoviesResponse>)
            val response = moviesRepository.getPopularMovies(1).first()
            assertEquals(
                response,
                DataState.Error<Nothing>(ErrorType.NoInternetConnection, ErrorModel.NoInternetConnection)
                        as DataState<*>
            )
        }

    @Test
    fun `call getPopularMovies and  return error state with GeneralError params in case of some error happens`() = runTest {
        setupPopularMovies(
            APIResponseState.NotValidResponse(
                1000,
                "something went wrong"
            ) as APIResponseState<MoviesResponse>
        )
        val response = moviesRepository.getPopularMovies(1).first()
        assertEquals(
            response,
            DataState.Error<Nothing>(
                ErrorType.GeneralError,
                ErrorModel.GeneralError(1000, "something went wrong")
            ) as DataState<*>
        )
    }
}