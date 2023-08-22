package com.vikmanz.shpppro.common.model

import java.io.Serializable

data class Account (
    val user: User,
    val accessToken: String,
    val refreshToken: String
) : Serializable