package com.vikmanz.shpppro.data.api

import com.vikmanz.shpppro.data.dto.ContactAddRequest
import com.vikmanz.shpppro.data.dto.ContactAddResponse
import com.vikmanz.shpppro.data.dto.ContactDeleteResponse
import com.vikmanz.shpppro.data.dto.TokenRefreshResponse
import com.vikmanz.shpppro.data.dto.UserAuthorizeRequest
import com.vikmanz.shpppro.data.dto.UserAuthorizeResponse
import com.vikmanz.shpppro.data.dto.UserRegisterRequest
import com.vikmanz.shpppro.data.dto.UserRegisterResponse
import com.vikmanz.shpppro.data.dto.UserEditRequest
import com.vikmanz.shpppro.data.dto.UserEditResponse
import com.vikmanz.shpppro.data.dto.UserGetAllResponse
import com.vikmanz.shpppro.data.dto.UserGetContactsResponse
import com.vikmanz.shpppro.data.dto.UserGetResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ShPPApi {

    @POST("users")
    @Headers("Content-Type: miltipart/form-data")
    suspend fun registerUser(
        @Body body: UserRegisterRequest
    ): UserRegisterResponse

    @POST("login")
    @Headers("Content-type: application/json")
    suspend fun authorizeUser(
        @Body body: UserAuthorizeRequest
    ): UserAuthorizeResponse

    @POST("refresh")
    suspend fun refreshToken(
        @Header("RefreshToken") refreshToken: String,
    ): TokenRefreshResponse

    @GET("users/{userId}")
    suspend fun getUser(
        @Header("Authorization") token: String,
        @Path("userId") userId: Int
    ): UserGetResponse

    @PUT("users/{userId}")
    @Headers("Content-type: application/json")
    suspend fun editUser(
        @Header("Authorization") token: String,
        @Path("userId") userId: Int,
        @Body body: UserEditRequest
    ): UserEditResponse

    @GET("users")
    suspend fun getAllUsers(
        @Header("Authorization") token: String,
    ): UserGetAllResponse

    @PUT("users/{userId}/contacts")
    @Headers("Content-type: application/json")
    suspend fun addContact(
        @Header("Authorization") token: String,
        @Path("userId") userId: Int,
        @Body body: ContactAddRequest
    ): ContactAddResponse

    @DELETE("users/{userId}/contacts/{contactId}")
    suspend fun deleteContact(
        @Header("Authorization") token: String,
        @Path("userId") userId: Int,
        @Path("contactId") contactId: Int
    ): ContactDeleteResponse

    @GET("users/{userId}/contacts")
    suspend fun getUserContacts(
        @Header("Authorization") token: String,
        @Path("userId") userId: Int
    ): UserGetContactsResponse

}