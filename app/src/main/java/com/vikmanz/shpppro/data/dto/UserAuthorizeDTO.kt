package com.vikmanz.shpppro.data.dto

import com.vikmanz.shpppro.data.model.User

data class UserAuthorizeRequest (
    val email: String,
    val password: String
)

data class UserAuthorizeResponse(
    val status: String,
    val code: Int,
    val message: String,
    val data: UserAuthorizeResponseBody
)

data class UserAuthorizeResponseBody(
    val user: User,
    val accessToken: String,
    val refreshToken: String
)