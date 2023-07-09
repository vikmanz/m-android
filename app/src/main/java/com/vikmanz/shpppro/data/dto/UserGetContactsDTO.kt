package com.vikmanz.shpppro.data.dto

import com.vikmanz.shpppro.common.model.User

data class UserGetContactsResponse(
    val status: String,
    val code: Int,
    val message: String,
    val data: UserGetContactsResponseBody
)

data class UserGetContactsResponseBody(
    val contacts: List<User>
)

fun UserGetContactsResponse.toListOfContacts() = data.contacts