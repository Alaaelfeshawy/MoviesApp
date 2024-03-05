package banquemisr.challenge05.domain.dto

import androidx.annotation.DrawableRes
import banquemisr.challenge05.domain.Constants.ErrorCodes.GENERAL_ERROR_CODE
import banquemisr.challenge05.domain.R

sealed class ErrorModel(
    val errorMessage: Any?,
    val errorCode: Int?,
    @DrawableRes val errorIcon: Int?
) {
     object NoInternetConnection : ErrorModel(R.string.no_internet_connection, GENERAL_ERROR_CODE, R.drawable.no_internet_connection)

    data class GeneralError(
        val code: Int?,
        val message: Any?,
        var icon: Int? = null
    ) : ErrorModel(message, code, icon)
}
