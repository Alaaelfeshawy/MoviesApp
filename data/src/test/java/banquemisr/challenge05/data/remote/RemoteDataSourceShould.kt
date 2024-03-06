package banquemisr.challenge05.data.remote

import banquemisr.challenge05.data.remote.api.API
import banquemisr.challenge05.data.remote.datasource.IRemoteDataSource
import banquemisr.challenge05.data.remote.datasource.RemoteDataSource
import banquemisr.challenge05.data.remote.internet.ICheckInternetConnection
import banquemisr.challenge05.data.remote.response.movies.MoviesResponse
import banquemisr.challenge05.data.remote.validate.APIResponseState
import banquemisr.challenge05.data.remote.validate.IValidateAPIResponse
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class RemoteDataSourceShould {

    private lateinit var remoteDataSource : IRemoteDataSource
    private val iValidateAPIResponse: IValidateAPIResponse = mockk()
    private val iCheckInternetConnection: ICheckInternetConnection = mockk()
    private val api: API = mockk()

    @Before
    fun setUp(){
        remoteDataSource = RemoteDataSource(
            iValidateAPIResponse = iValidateAPIResponse,
            iCheckInternetConnection = iCheckInternetConnection,
            api = api
        )
    }

    //test playing movies section
    private fun setupPlayingMovies(isConnected: Boolean = true, apiResponseState: APIResponseState<Any>) =
        runTest {

            coEvery{ iValidateAPIResponse.validateResponse(api.getPlayingMovies(1))} returns
                    apiResponseState as APIResponseState<MoviesResponse>


            every {  iCheckInternetConnection.isNetworkAvailable()} returns isConnected
        }

    @Test
    fun `call getPlayingMovies from API once`() = runTest {

        setupPlayingMovies(true, APIResponseState.ValidResponse(MoviesResponse()))

        remoteDataSource.getPlayingMovies(1)

        coVerify { api.getPlayingMovies(eq(1)) }
    }

    @Test
    fun `not call getPlayingMovies and return no internetConnection in case of there is no internetConnection`() =
        runTest {
            setupPlayingMovies(false, APIResponseState.NoInternetConnection)
            val result = remoteDataSource.getPlayingMovies(1)
            assertEquals(APIResponseState.NoInternetConnection as APIResponseState<*>, result)
        }

    @Test
    fun `call getPlayingMovies and return the valid response in case of there is internetConnection and response is valid`() =
        runTest {
            setupPlayingMovies(true, APIResponseState.ValidResponse(MoviesResponse()))
            val result = remoteDataSource.getPlayingMovies(1)
            assertEquals(APIResponseState.ValidResponse(MoviesResponse()), result)
        }

    @Test
    fun `call getPlayingMovies not valid response in case of any error happened`() = runTest {
        setupPlayingMovies(true, APIResponseState.NotValidResponse(1000, "exception"))
        val result = remoteDataSource.getPlayingMovies(1)
        assertEquals(
            APIResponseState.NotValidResponse(1000, "exception") as APIResponseState<*>,
            result
        )
    }

    // test upcoming section
    private fun setupUpcomingMovies(isConnected: Boolean = true, apiResponseState: APIResponseState<Any>) =
        runTest {

            coEvery{ iValidateAPIResponse.validateResponse(api.getUpcomingMovies(1))} returns
                    apiResponseState as APIResponseState<MoviesResponse>


            every {  iCheckInternetConnection.isNetworkAvailable()} returns isConnected
        }

    @Test
    fun `call getUpcomingMovies from API once`() = runTest {

        setupUpcomingMovies(true, APIResponseState.ValidResponse(MoviesResponse()))

        remoteDataSource.getUpcomingMovies(1)

        coVerify { api.getUpcomingMovies(eq(1)) }
    }

    @Test
    fun `not call getUpcomingMovies and return no internetConnection in case of there is no internetConnection`() =
        runTest {
            setupUpcomingMovies(false, APIResponseState.NoInternetConnection)
            val result = remoteDataSource.getUpcomingMovies(1)
            assertEquals(APIResponseState.NoInternetConnection as APIResponseState<*>, result)
        }

    @Test
    fun `call getUpcomingMovies and return the valid response in case of there is internetConnection and response is valid`() =
        runTest {
            setupUpcomingMovies(true, APIResponseState.ValidResponse(MoviesResponse()))
            val result = remoteDataSource.getUpcomingMovies(1)
            assertEquals(APIResponseState.ValidResponse(MoviesResponse()), result)
        }

    @Test
    fun `call getUpcomingMovies not valid response in case of any error happened`() = runTest {
        setupUpcomingMovies(true, APIResponseState.NotValidResponse(1000, "exception"))
        val result = remoteDataSource.getUpcomingMovies(1)
        assertEquals(
            APIResponseState.NotValidResponse(1000, "exception") as APIResponseState<*>,
            result
        )
    }

    // test popular section
    private fun setupPopularMovies(isConnected: Boolean = true, apiResponseState: APIResponseState<Any>) =
        runTest {

            coEvery{ iValidateAPIResponse.validateResponse(api.getPopularMovies(1))} returns
                    apiResponseState as APIResponseState<MoviesResponse>


            every {  iCheckInternetConnection.isNetworkAvailable()} returns isConnected
        }

    @Test
    fun `call getPopularMovies from API once`() = runTest {

        setupPopularMovies(true, APIResponseState.ValidResponse(MoviesResponse()))

        remoteDataSource.getPopularMovies(1)

        coVerify { api.getPopularMovies(eq(1)) }
    }

    @Test
    fun `not call getPopularMovies and return no internetConnection in case of there is no internetConnection`() =
        runTest {
            setupPopularMovies(false, APIResponseState.NoInternetConnection)
            val result = remoteDataSource.getPopularMovies(1)
            assertEquals(APIResponseState.NoInternetConnection as APIResponseState<*>, result)
        }

    @Test
    fun `call getPopularMovies and return the valid response in case of there is internetConnection and response is valid`() =
        runTest {
            setupPopularMovies(true, APIResponseState.ValidResponse(MoviesResponse()))
            val result = remoteDataSource.getPopularMovies(1)
            assertEquals(APIResponseState.ValidResponse(MoviesResponse()), result)
        }

    @Test
    fun `call getPopularMovies not valid response in case of any error happened`() = runTest {
        setupPopularMovies(true, APIResponseState.NotValidResponse(1000, "exception"))
        val result = remoteDataSource.getPopularMovies(1)
        assertEquals(
            APIResponseState.NotValidResponse(1000, "exception") as APIResponseState<*>,
            result
        )
    }

}