package com.vikmanz.shpppro.data.dto

import com.vikmanz.shpppro.data.model.ContactItem
import com.vikmanz.shpppro.data.model.User

data class UserGetContactsResponse(
    val status: String,
    val code: Int,
    val message: String,
    val data: UserGetContactsResponseBody
)

data class UserGetContactsResponseBody(
    val contacts: List<User>
)

fun UserGetContactsResponse.toListOfContactItems() = data.contacts.map {
    ContactItem(contact = it)
}