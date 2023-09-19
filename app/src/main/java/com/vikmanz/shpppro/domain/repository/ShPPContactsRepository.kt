package com.vikmanz.shpppro.domain.repository

import com.vikmanz.shpppro.data.model.ContactItem
import com.vikmanz.shpppro.data.model.User
import kotlinx.coroutines.flow.StateFlow
import ua.digitalminds.fortrainerapp.data.result.ApiResult

interface ShPPContactsRepository {

    val contactList: StateFlow<List<ContactItem>>

    suspend fun getAllUsers(): ApiResult<List<User>>
    suspend fun addContact(contactId: Int): ApiResult<List<User>>
    suspend fun deleteContact(contactId: Int): ApiResult<List<User>>
    suspend fun getUserContacts(): ApiResult<List<User>>
    fun findContact(contactId: Int): ContactItem?
}