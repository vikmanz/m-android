package com.example.domain.interfaces

import com.example.domain.entities.ContactAddRequestEntity
import com.example.domain.entities.ContactAddResponseEntity
import com.example.domain.entities.ContactDeleteResponseEntity
import com.example.domain.entities.TokenRefreshResponseEntity
import com.example.domain.entities.UserAuthorizeRequestEntity
import com.example.domain.entities.UserAuthorizeResponseEntity
import com.example.domain.entities.UserCreateRequestEntity
import com.example.domain.entities.UserCreateResponseEntity
import com.example.domain.entities.UserEditRequestEntity
import com.example.domain.entities.UserEditResponseEntity
import com.example.domain.entities.UserGetAllResponseEntity
import com.example.domain.entities.UserGetContactsResponseEntity
import com.example.domain.entities.UserGetResponseEntity
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ShPpApi {

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
        @Body body:ContactAddRequestEntity
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