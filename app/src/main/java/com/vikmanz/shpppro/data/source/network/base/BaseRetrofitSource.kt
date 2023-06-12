package com.vikmanz.shpppro.data.source.network.base

import android.util.Log
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.JsonEncodingException
import retrofit2.HttpException
import java.io.IOException

open class BaseRetrofitSource (
    retrofitConfig: RetrofitConfig
){

    val retrofit = retrofitConfig.retrofit

    private val errorAdapter = retrofitConfig.moshi.adapter(ErrorResponseBody::class.java)


    suspend fun <T> wrapRetrofitExceptions(block: suspend  () -> T): T {
        return try {
            block()
        }
        catch (e: JsonDataException) {
            Log.d("MyLog", "Error parser moshi 1")
            throw RuntimeException()
        }
        catch (e: JsonEncodingException) {
            Log.d("MyLog", "Error parser moshi 2")
            throw RuntimeException()
        }
        catch (e: HttpException) {
            throw createBackendException(e)
        }
        catch (e: IOException) {
            Log.d("MyLog", "Error IO - connection error")
            throw RuntimeException()
        }
    }

    private fun createBackendException(e: HttpException): Exception {
        try {
            val errorBody = errorAdapter.fromJson(
                e.response()!!.errorBody()!!.string()
            )
            Log.d("MyLog", "Http error 1: $errorBody")
            throw RuntimeException()
        }
        catch (e: Exception) {
            Log.d("MyLog", "Http error 2")
            throw RuntimeException()
        }
    }

    class ErrorResponseBody(
        val error: String
    )

}