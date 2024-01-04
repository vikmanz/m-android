package com.vikmanz.shpppro.data.dto

import com.vikmanz.shpppro.data.model.User

data class TokenRefreshResponse(
    val status: String,
    val code: Int,
    val message: String,
    val data: TokenRefreshResponseBody
)

data class TokenRefreshResponseBody(
    val accessToken: String,
    val refreshToken: String
)
