package com.example.domain.entities

import com.example.domain.model.User

data class UserEditRequestEntity (
    val name: String? = null,
    val phone: String? = null,
    val address: String? = null,
    val career: String? = null,
    val birthday: String? = null,
    val facebook: String? = null,
    val instagram: String? = null,
    val twitter: String? = null,
    val linkedin: String? = null
)

data class UserEditResponseEntity(
    val status: String,
    val code: Int,
    val message: String,
    val data: UserEditResponseBody
)

data class UserEditResponseBody(
    val user: User
)