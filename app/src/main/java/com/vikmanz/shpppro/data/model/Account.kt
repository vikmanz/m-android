package com.vikmanz.shpppro.data.model

data class Account (
    val user: User,
    val accessToken: String,
    val refreshToken: String
)