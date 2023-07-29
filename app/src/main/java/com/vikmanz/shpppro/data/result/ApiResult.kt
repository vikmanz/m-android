package ua.digitalminds.fortrainerapp.data.result

// https://medium.com/@douglas.iacovelli/how-to-handle-errors-with-retrofit-and-coroutines-33e7492a912
sealed class ApiResult<out T> {

    data class Success<out T>(
        val value: T
    ) : ApiResult<T>()

    data class ServerError(
        val error: ApiSafeCallerError
    ) : ApiResult<Nothing>()

    object NetworkError : ApiResult<Nothing>()

    object Loading : ApiResult<Nothing>()

}