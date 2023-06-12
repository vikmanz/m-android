package com.vikmanz.shpppro.data.entities

import com.vikmanz.shpppro.data.model.User

data class ContactAddRequestEntity (
    val contactId: Int
)

data class ContactAddResponseEntity(
    val status: String,
    val code: Int,
    val message: String,
    val data: ContactAddResponseBody
)

data class ContactAddResponseBody(
    val contacts: List<User>
)