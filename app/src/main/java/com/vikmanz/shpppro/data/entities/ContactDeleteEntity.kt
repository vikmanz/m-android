package com.vikmanz.shpppro.data.entities

import com.vikmanz.shpppro.data.model.User
data class ContactDeleteResponseEntity(
    val status: String,
    val code: Int,
    val message: String,
    val data: ContactDeleteResponseBody
)

data class ContactDeleteResponseBody(
    val contacts: List<User>
)