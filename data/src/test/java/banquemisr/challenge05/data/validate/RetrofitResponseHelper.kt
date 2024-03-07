package banquemisr.challenge05.data.validate

import banquemisr.challenge05.data.Constants.MimeTypes.APPLICATION_JSON
import banquemisr.challenge05.data.remote.response.ErrorResponse
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response


class RetrofitResponseHelper(private val mGsonBuilder: Gson) {

    fun getErrorMessage():String{
        return "Error"
    }
    fun successResponse(): Response<Any> {
        return Response.success(listOf<Any>())
    }

    fun emptyErrorResponse(): Response<Any> {
        return Response.error(600, "".toResponseBody())
    }

    fun errorResponse(desiredTestMessage: String = "", desiredTestCode: Int = 600): Response<Any> {
        val errorResponse = mGsonBuilder.toJson(ErrorResponse(statusMessage = desiredTestMessage))
        val errorResponseBody = errorResponse.toResponseBody(APPLICATION_JSON.toMediaTypeOrNull())
        return Response.error(desiredTestCode, errorResponseBody)
    }
}