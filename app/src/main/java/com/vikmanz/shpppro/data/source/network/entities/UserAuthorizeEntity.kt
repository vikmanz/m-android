package com.vikmanz.shpppro.data.source.network.entities

import com.vikmanz.shpppro.data.model.User

data class UserAuthorizeRequestEntity (
    val email: String,
    val password: String
)

data class UserAuthorizeResponseEntity(
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