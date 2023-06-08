package com.example.domain.entities

import com.example.domain.model.User

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