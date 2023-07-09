package com.vikmanz.shpppro.data.dto

import com.vikmanz.shpppro.data.model.User
data class ContactDeleteResponse(
    val status: String,
    val code: Int,
    val message: String,
    val data: ContactDeleteResponseBody
)

data class ContactDeleteResponseBody(
    val contacts: List<User>
)

fun ContactDeleteResponse.toListOfContacts() = data.contacts