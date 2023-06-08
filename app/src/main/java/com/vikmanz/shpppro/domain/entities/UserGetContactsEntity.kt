package com.example.domain.entities

import com.example.domain.model.User

data class UserGetContactsResponseEntity(
    val status: String,
    val code: Int,
    val message: String,
    val data: UserGetContactsResponseBody
)

data class UserGetContactsResponseBody(
    val contacts: List<User>
)