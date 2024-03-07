package banquemisr.challenge05.data.remote.datasource

import banquemisr.challenge05.data.remote.api.API
import banquemisr.challenge05.data.remote.response.movies.MoviesResponse
import banquemisr.challenge05.data.remote.internet.ICheckInternetConnection
import banquemisr.challenge05.data.remote.validate.APIResponseState
import banquemisr.challenge05.data.remote.validate.IValidateAPIResponse
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val iValidateAPIResponse: IValidateAPIResponse,
    private val iCheckInternetConnection: ICheckInternetConnection,
    private val api: API
) : IRemoteDataSource {

    override suspend fun getPlayingMovies(page: Int): APIResponseState<MoviesResponse> {
        return if (iCheckInternetConnection.isNetworkAvailable()) {
            val response = api.getPlayingMovies(page)
            iValidateAPIResponse.validateResponse(response)
        } else APIResponseState.NoInternetConnection
    }

    override suspend fun getPopularMovies(page: Int): APIResponseState<MoviesResponse> {
        return if (iCheckInternetConnection.isNetworkAvailable()) {
            val response = api.getPopularMovies(page)
            iValidateAPIResponse.validateResponse(response)
        } else APIResponseState.NoInternetConnection
    }

    override suspend fun getUpcomingMovies(page: Int): APIResponseState<MoviesResponse> {
        return if (iCheckInternetConnection.isNetworkAvailable()) {
            val response = api.getUpcomingMovies(page)
            iValidateAPIResponse.validateResponse(response)
        } else APIResponseState.NoInternetConnection
    }




}