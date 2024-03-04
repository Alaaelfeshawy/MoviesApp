package banquemisr.challenge05.domain.dto

sealed class DataState<out T> {
    data class Success<T>(val date: T?) : DataState<T>()
    data class Error<T>(val errorType: ErrorType?, val errorModel: ErrorModel?) : DataState<Nothing>()
}

fun <T : Any> DataState<T>.getErrorType(): ErrorType? {
    return if (this is DataState.Error<*>)
        this.errorType
    else
        null
}

fun <T : Any> DataState<T>.getErrorModel(): ErrorModel? {
    return if (this is DataState.Error<*>)
        this.errorModel
    else
        null
}

fun <T : Any> DataState<T>.getData(): T? {
    return if (this is DataState.Success<T>)
        this.date
    else
        null
}
