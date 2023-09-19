package com.vikmanz.shpppro.data.dto

import com.vikmanz.shpppro.data.model.User

data class UserRegisterRequest (
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

data class UserRegisterResponse(
    val status: String,
    val code: Int,
    val message: String,
    val data: UserRegisterResponseBody
)

data class UserRegisterResponseBody(
    val user: User,
    val accessToken: String,
    val refreshToken: String
)
