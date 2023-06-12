package com.vikmanz.shpppro.data.entities

import com.vikmanz.shpppro.data.model.User

data class UserGetResponseEntity(
    val status: String,
    val code: Int,
    val message: String,
    val data: UserGetResponseBody
)

data class UserGetResponseBody(
    val user: User
)