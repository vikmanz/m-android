package com.example.domain.entities

import com.example.domain.model.User

data class UserGetAllResponseEntity(
    val status: String,
    val code: Int,
    val message: String,
    val data: UserGetAllResponseBody
)

data class UserGetAllResponseBody(
    val users: List<User>
)