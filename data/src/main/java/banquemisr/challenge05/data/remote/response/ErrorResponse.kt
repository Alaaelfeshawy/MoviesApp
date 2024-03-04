package banquemisr.challenge05.data.remote.response

import com.google.gson.annotations.SerializedName
data class ErrorResponse(
    @SerializedName("status_message") val statusMessage: String? = null,
    @SerializedName("status_code") val statusCode: Int? = null,
    @SerializedName("success") val isSuccess: Boolean? = null
)
