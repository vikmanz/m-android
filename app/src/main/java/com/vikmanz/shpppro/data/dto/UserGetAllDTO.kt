package com.vikmanz.shpppro.data.dto

import com.vikmanz.shpppro.common.model.User

data class UserGetAllResponse(
    val status: String,
    val code: Int,
    val message: String,
    val data: UserGetAllResponseBody
)

data class UserGetAllResponseBody(
    val users: List<User>
)

fun UserGetAllResponse.toListOfUsers() = data.users