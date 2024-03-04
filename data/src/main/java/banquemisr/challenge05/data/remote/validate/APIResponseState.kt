package banquemisr.challenge05.data.remote.validate

sealed class APIResponseState<out T > {
    data class ValidResponse<T>(val data: T?) : APIResponseState<T>()
    object NoInternetConnection : APIResponseState<Nothing>()
    data class NotValidResponse(val errorCode: Int, val message: Any) : APIResponseState<Nothing>()
}