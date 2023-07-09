package com.vikmanz.shpppro.common.model

data class Account (
    val user: User,
    val accessToken: String,
    val refreshToken: String
)