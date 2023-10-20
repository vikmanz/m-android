package com.vikmanz.shpppro.data.dto

import com.vikmanz.shpppro.utils.extensions.log
import com.vikmanz.shpppro.data.model.User

data class UserGetAllResponse(
    val status: String,
    val code: Int,
    val message: String,
    val data: UserGetAllResponseBody
)

data class UserGetAllResponseBody(
    val users: List<User>
)

fun UserGetAllResponse.toListOfUsers(): List<User> {
    log("to list!")
    return data.users
}