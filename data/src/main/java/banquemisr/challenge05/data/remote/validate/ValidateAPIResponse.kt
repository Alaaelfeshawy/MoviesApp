package banquemisr.challenge05.data.remote.validate

import banquemisr.challenge05.data.remote.response.ErrorResponse
import com.google.gson.Gson
import retrofit2.HttpException
import retrofit2.Response
import javax.inject.Inject
import banquemisr.challenge05.domain.R


class ValidateAPIResponse @Inject constructor(
    private val mGson: Gson
) :
    IValidateAPIResponse {
    override suspend fun <T : Any> validateResponse(api: Response<T>): APIResponseState<T> {
        return try {
            val apiResponse = api.body()
            if (api.isSuccessful && apiResponse != null)
                APIResponseState.ValidResponse(apiResponse)
            else {
                val errorBody =
                    mGson.fromJson(api.errorBody()?.charStream(), ErrorResponse::class.java)
                if (errorBody == null)
                    APIResponseState.NotValidResponse(
                        api.code(),
                        R.string.something_went_wrong
                    )
                else {
                    APIResponseState.NotValidResponse(
                        errorBody.statusCode ?: api.code(),
                        errorBody.statusMessage ?: R.string.something_went_wrong
                    )

                }
            }
        } catch (exception: HttpException) {
            APIResponseState.NotValidResponse(
                exception.code(),
                R.string.something_went_wrong
            )
        } catch (e: Exception) {
            APIResponseState.NotValidResponse(
                9999,
                e.message ?: R.string.something_went_wrong
            )
        }
    }
}