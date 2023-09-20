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

        val braceCount = bodyString.count { it == '{' || it == '}' }
        val isBracketError = braceCount % 2 != 0
        val newBodyString = if (isBracketError) "$bodyString}".also { catch() } else bodyString

        val body = newBodyString.toResponseBody(contentType)
        return response.newBuilder().body(body).build()
    }

    companion object {

        private val catch =
            { log("=============== !!! CATCH } SHREDINGERA !!! ===============") }

    }
}

/*
 * Create user   {"status":"success","code":200,"data":{"user": {...user data}, "accessToken": "token", "refreshToken": "refreshToken"}}
 * Authorize user   {"status":"success","code":200,"data":{"user": {...user data},"accessToken":"token", "refreshToken":"refreshToken"}}
 * Refresh token   {"status":"success","code":200,"data":{"accessToken":"newAccessToken", "refreshToken": "newRefreshToken"}}
 * Get user {"status":"success","code":200,"message":"","data":{"user":{"aaa"}}}
 * Edit user {"status":"success","code":200,"message":"","data":{"user":{"aaa"}}}
 * Get all users {"status":"success","code":200,"message":"","data":{"users":[{"aaa"}]}}
 * Add contact {"status":"success","code":200,"message":"Contact added","data":{"contacts": [{"aaa"}]}}
 * Delete contact {"status":"success","code":200,"message":"Contact deleted","data":{"contacts": [{"aaa"}]}}
 * Get user contacts {"status":"success","code":200,"message":"","data":{"contacts":[{"aaa"}]}}
 *
 *
 * {
 * "status":"success",
 * "code":200,
 * "message":"User updated",
 * "data":
 *      {"user":
 *          {"id":388,"name":"vikmanz","email":"viktor.manza11@gmail.com","phone":"7777754432",
 *              "career":null,"address":null,"birthday":null,"facebook":null,"instagram":null,"twitter":null,
 *              "linkedin":null,"image":null,"created_at":"2023-09-20T20:18:40.000000Z",
 *          "updated_at":"2023-09-20T20:18:55.000000Z"
 *          }
 *      }
 * }}
 *
 *
 *
 *
 *
 *  {
 * "status":"success",
 * "code":200,
 * "message":"User updated",
 * "data":{
 *      "user":{
 *              "id":384,"name":"vik","email":"vik@g.com","phone":"ddddd","career":null,"address":null,
 *              "birthday":null,"facebook":null,"instagram":null,"twitter":null,"linkedin":null,
 *              "image":null,"created_at":"2023-09-20T18:46:28.000000Z","updated_at":"2023-09-20T18:46:40.000000Z"
 *              }
 *          }
 *
 *
 */