package com.example.domain.entities

import com.example.domain.model.User
data class ContactDeleteResponseEntity(
    val status: String,
    val code: Int,
    val message: String,
    val data: ContactDeleteResponseBody
)

data class ContactDeleteResponseBody(
    val contacts: List<User>
)