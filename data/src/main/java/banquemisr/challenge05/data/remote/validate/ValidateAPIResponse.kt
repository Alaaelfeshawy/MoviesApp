package banquemisr.challenge05.data.remote.validate

import banquemisr.challenge05.data.Constants.GENERAL_CODE
import banquemisr.challenge05.data.Constants.NO_CONNECTION_CODE
import banquemisr.challenge05.data.Constants.TIME_OUT_CODE
import banquemisr.challenge05.data.remote.response.ErrorResponse
import com.google.gson.Gson
import retrofit2.HttpException
import retrofit2.Response
import javax.inject.Inject
import banquemisr.challenge05.domain.R
import java.io.IOException
import java.net.SocketTimeoutException


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
        } catch (throwable: HttpException) {
            APIResponseState.NotValidResponse(errorCode = throwable.code(), message = R.string.something_went_wrong)
        } catch (s: SocketTimeoutException) {
            APIResponseState.NotValidResponse(errorCode =TIME_OUT_CODE, message = s.message ?: R.string.something_went_wrong)
        }
        catch (io: IOException){
            APIResponseState.NotValidResponse(errorCode = NO_CONNECTION_CODE, message = io.message ?: R.string.something_went_wrong)
        }
        catch (e: Exception) {
            APIResponseState.NotValidResponse(
                GENERAL_CODE,
                e.message ?: R.string.something_went_wrong
            )
        }
        catch (e: Throwable) {
            APIResponseState.NotValidResponse(
                GENERAL_CODE,
                e.message ?: R.string.something_went_wrong
            )
        }
    }
}