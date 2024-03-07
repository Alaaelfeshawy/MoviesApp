package banquemisr.challenge05.data.repository

import banquemisr.challenge05.domain.dto.DataState
import banquemisr.challenge05.domain.dto.ErrorModel
import banquemisr.challenge05.domain.dto.ErrorType.GeneralError
import banquemisr.challenge05.domain.dto.ErrorType.NoInternetConnection
import banquemisr.challenge05.data.remote.validate.APIResponseState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

suspend fun <T : Any, R : Any> APIResponseState<T>.mapToFlowDataState(mapSuccessResponse: (T?) -> R): Flow<DataState<R>> {
    val dataState = when (this) {
        is APIResponseState.ValidResponse -> {
            val data = this.data
            DataState.Success(mapSuccessResponse(data))
        }

        is APIResponseState.NotValidResponse -> {
            val message = this.message
            val code = this.errorCode
            DataState.Error<Nothing>(GeneralError, ErrorModel.GeneralError(code, message))
        }

        APIResponseState.NoInternetConnection -> DataState.Error<Nothing>(NoInternetConnection, ErrorModel.NoInternetConnection)
    }
    return flow { emit(dataState) }
}