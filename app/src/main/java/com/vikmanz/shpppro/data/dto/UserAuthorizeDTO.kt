package com.vikmanz.shpppro.data.dto

import com.vikmanz.shpppro.common.model.Account
import com.vikmanz.shpppro.common.model.User

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


fun UserAuthorizeResponse.toAccount() = Account(
    user = this.data.user,
    accessToken = this.data.accessToken,
    refreshToken = this.data.refreshToken
)