package com.vikmanz.shpppro.data.result

import com.google.gson.Gson
import com.vikmanz.shpppro.common.extensions.log
import com.vikmanz.shpppro.data.dto.ErrorServerResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response
import ua.digitalminds.fortrainerapp.data.result.ApiResult
import ua.digitalminds.fortrainerapp.data.result.ApiSafeCallerError
import ua.digitalminds.fortrainerapp.data.result.ApiSafeCallerError.UNKNOWN_EXCEPTION
import java.io.IOException

private const val DEBUG = true

object ApiSafeCaller {

    suspend fun <T> safeApiCall(
        apiCall: suspend () -> T
    ): ApiResult<T> =

        withContext(Dispatchers.IO) {

            try {
                log("try...", DEBUG)
                ApiResult.Success(apiCall.invoke())
            } catch (throwable: Throwable) {

                log("Catch!", DEBUG)

                when (throwable) {

                    is IOException ->  handleIOError()
                    is HttpException -> handleHttpError(throwable)
                    else -> handleUnknownError()

                }
            }
        }


    private fun handleIOError(): ApiResult.NetworkError {
        log("IO Exception!", DEBUG)
        return ApiResult.NetworkError
    }

//    private fun <T> tryParsingAgain(throwable: Throwable): ApiResult.Success<T>? {
//        val json = throwable.response()?.errorBody()?.string()
//
//        return try {
//            log("try convert first", DEBUG)
//            val errorResponse = convertErrorBody(json, ErrorServerResponse::class.java)
//            return ApiResult.Success(value = )
//        }
//        catch (e: Exception) {
//            return null
//        }
//    }

    private fun handleUnknownError(): ApiResult.ServerError {
        log("Other Exception!", DEBUG)
        return ApiResult.ServerError(UNKNOWN_EXCEPTION)
    }

    private fun handleHttpError(throwable: HttpException): ApiResult.ServerError {
        log("HttpException Exception!", DEBUG)
        val code = throwable.code()
        val json = throwable.response()?.errorBody()?.string()
        return try {
            log("try convert first", DEBUG)
            val errorResponse = convertErrorBody(json, ErrorServerResponse::class.java)
            responseToServerError(errorResponse)
        } catch (e: Exception) {
            ApiResult.ServerError(UNKNOWN_EXCEPTION)
        }
    }

    private fun <T> convertErrorBody(
        jsonString: String?,
        classOfError: Class<T>
    ): T? {
        return jsonString?.let {
            log("Try convert error to class [${classOfError.simpleName}]", DEBUG)
            log("Error json [$it]", DEBUG)
            val errorResponse = Gson().fromJson(it, classOfError)
            log("error response: $errorResponse", DEBUG)
            errorResponse
        }
    }

    private fun responseToServerError(errorResponse: ErrorServerResponse?): ApiResult.ServerError {
        val error = ApiSafeCallerError from errorResponse?.message
        return if (error != null) {
            log("Error is not null", DEBUG)
            ApiResult.ServerError(error)
        } else {
            log("!!!Error is null!!!", DEBUG)
            ApiResult.ServerError(ApiSafeCallerError.UNKNOWN_HTTP_EXCEPTION)
        }
    }
}