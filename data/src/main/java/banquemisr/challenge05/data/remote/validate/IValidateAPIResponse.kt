package banquemisr.challenge05.data.remote.validate

import retrofit2.Response

interface IValidateAPIResponse {
    suspend fun <T : Any> validateResponse(api: Response<T>): APIResponseState<T>
}