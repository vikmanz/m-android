package com.vikmanz.shpppro.data.entities

import com.vikmanz.shpppro.data.model.User

data class UserGetContactsResponseEntity(
    val status: String,
    val code: Int,
    val message: String,
    val data: UserGetContactsResponseBody
)

data class UserGetContactsResponseBody(
    val contacts: List<User>
)