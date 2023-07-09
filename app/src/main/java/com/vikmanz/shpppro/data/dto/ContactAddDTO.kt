package com.vikmanz.shpppro.data.dto

import com.vikmanz.shpppro.common.model.User

data class ContactAddRequest (
    val contactId: Int
)

data class ContactAddResponse(
    val status: String,
    val code: Int,
    val message: String,
    val data: ContactAddResponseBody
)

data class ContactAddResponseBody(
    val contacts: List<User>
)

fun ContactAddResponse.toListOfContacts() = data.contacts