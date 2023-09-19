package com.vikmanz.shpppro.data.dto

import com.vikmanz.shpppro.data.model.User

data class UserGetResponse(
    val status: String,
    val code: Int,
    val message: String,
    val data: UserGetResponseBody
)

data class UserGetResponseBody(
    val user: User
)

fun UserGetResponse.toUser() = data.user