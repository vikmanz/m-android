package com.vikmanz.shpppro.data.utils

import com.vikmanz.shpppro.common.extensions.log
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody

class MyResponseInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val response = chain.proceed(chain.request())

        val contentType = response.body?.contentType()
        val bodyString = response.body?.string() ?: ""

        val newBodyString =
            if (bodyString.takeLast(2) == "]}") "$bodyString}".also { catch.invoke() } else bodyString

        val body = newBodyString.toResponseBody(contentType)

        return response.newBuilder().body(body).build()
    }

    companion object {
        private val catch =
            { log("=============== !!! CATCH } SHREDINGERA !!! ===============") }

    }
}