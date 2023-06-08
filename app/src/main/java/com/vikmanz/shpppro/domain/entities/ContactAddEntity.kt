package com.example.domain.entities

import com.example.domain.model.User

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