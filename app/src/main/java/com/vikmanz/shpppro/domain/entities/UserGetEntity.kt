package com.example.domain.entities

import com.example.domain.model.User

data class UserGetResponseEntity(
    val status: String,
    val code: Int,
    val message: String,
    val data: UserGetResponseBody
)

data class UserGetResponseBody(
    val user: User
)