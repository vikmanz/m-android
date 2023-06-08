package com.example.domain.entities

import com.example.domain.model.User

data class UserCreateRequestEntity (
    val email: String,
    val password: String,
    val name: String? = null,
    val phone: String? = null,
    val address: String? = null,
    val career: String? = null,
    val birthday: String? = null,
    val facebook: String? = null,
    val instagram: String? = null,
    val twitter: String? = null,
    val linkedin: String? = null,
    val image: String? = null
)

data class UserCreateResponseEntity(
    val status: String,
    val code: Int,
    val message: String,
    val data: UserCreateResponseBody
)

data class UserCreateResponseBody(
    val user: User,
    val accessToken: String,
    val refreshToken: String
)