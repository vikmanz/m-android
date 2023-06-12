package com.vikmanz.shpppro.data.entities

data class TokenRefreshResponseEntity(
    val status: String,
    val code: Int,
    val message: String,
    val data: TokenRefreshResponseBody
)

data class TokenRefreshResponseBody(
    val accessToken: String,
    val refreshToken: String
)