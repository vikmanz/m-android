package com.vikmanz.shpppro.data.source.network.entities

import com.vikmanz.shpppro.data.model.User

data class UserGetAllResponseEntity(
    val status: String,
    val code: Int,
    val message: String,
    val data: UserGetAllResponseBody
)

data class UserGetAllResponseBody(
    val users: List<User>
)