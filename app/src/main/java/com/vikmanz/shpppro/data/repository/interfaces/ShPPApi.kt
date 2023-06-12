package com.vikmanz.shpppro.data.repository.interfaces

import com.vikmanz.shpppro.data.entities.ContactAddRequestEntity
import com.vikmanz.shpppro.data.entities.ContactAddResponseEntity
import com.vikmanz.shpppro.data.entities.ContactDeleteResponseEntity
import com.vikmanz.shpppro.data.entities.TokenRefreshResponseEntity
import com.vikmanz.shpppro.data.entities.UserAuthorizeRequestEntity
import com.vikmanz.shpppro.data.entities.UserAuthorizeResponseEntity
import com.vikmanz.shpppro.data.entities.UserCreateRequestEntity
import com.vikmanz.shpppro.data.entities.UserCreateResponseEntity
import com.vikmanz.shpppro.data.entities.UserEditRequestEntity
import com.vikmanz.shpppro.data.entities.UserEditResponseEntity
import com.vikmanz.shpppro.data.entities.UserGetAllResponseEntity
import com.vikmanz.shpppro.data.entities.UserGetContactsResponseEntity
import com.vikmanz.shpppro.data.entities.UserGetResponseEntity
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
    suspend fun createUser(
        @Body body: UserCreateRequestEntity
    ): UserCreateResponseEntity

    @POST("login")
    @Headers("Content-type: application/json")
    suspend fun authorizeUser(
        @Body body: UserAuthorizeRequestEntity
    ): UserAuthorizeResponseEntity

    @POST("refresh")
    suspend fun refreshToken(
        @Header("RefreshToken") refreshToken: String,
    ): TokenRefreshResponseEntity

    @GET("users/{userId}")
    suspend fun getUser(
        @Header("Authorization") token: String,
        @Path("userId") userId: Int
    ): UserGetResponseEntity

    @PUT("users/{userId}")
    @Headers("Content-type: application/json")
    suspend fun editUser(
        @Header("Authorization") token: String,
        @Path("userId") userId: Int,
        @Body body: UserEditRequestEntity
    ): UserEditResponseEntity

    @GET("users")
    suspend fun getAllUsers(
        @Header("Authorization") token: String,
    ): UserGetAllResponseEntity

    @PUT("users/{userId}/contacts")
    @Headers("Content-type: application/json")
    suspend fun addContact(
        @Header("Authorization") token: String,
        @Path("userId") userId: Int,
        @Body body: ContactAddRequestEntity
    ): ContactAddResponseEntity

    @DELETE("users/{userId}/contacts/{contactId}")
    suspend fun deleteContact(
        @Header("Authorization") token: String,
        @Path("userId") userId: Int,
        @Path("contactId") contactId: Int
    ): ContactDeleteResponseEntity

    @GET("users/{userId}/contacts")
    suspend fun getUserContacts(
        @Header("Authorization") token: String,
        @Path("userId") userId: Int
    ): UserGetContactsResponseEntity

}