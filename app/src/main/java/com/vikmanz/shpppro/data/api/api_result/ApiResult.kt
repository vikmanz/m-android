package ua.digitalminds.fortrainerapp.data.result

import com.vikmanz.shpppro.data.api.api_result.ApiSafeCallerError

// https://medium.com/@douglas.iacovelli/how-to-handle-errors-with-retrofit-and-coroutines-33e7492a912
sealed class ApiResult<out T> {

    data class Success<out T>(
        val value: T
    ) : ApiResult<T>()

    data class ServerError(
        val error: ApiSafeCallerError
    ) : ApiResult<Nothing>()

    data object NetworkError : ApiResult<Nothing>()

    data object Loading : ApiResult<Nothing>()

    fun convertToBoolean() =
        when (this) {
            is Success<*> -> Success(value = true)
            is Loading -> Loading
            is NetworkError -> NetworkError
            is ServerError -> ServerError(error = this.error)
        }
}