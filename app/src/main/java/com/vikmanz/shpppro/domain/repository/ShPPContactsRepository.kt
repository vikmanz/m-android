package com.vikmanz.shpppro.domain.repository

import com.vikmanz.shpppro.common.model.ContactItem
import com.vikmanz.shpppro.common.model.User
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import ua.digitalminds.fortrainerapp.data.result.ApiResult
import java.net.URL

interface ShPPContactsRepository {

    val contactList: StateFlow<List<ContactItem>>
    suspend fun getAllUsers(user: User): ApiResult<List<User>>
    suspend fun addContact(contactId: Int): ApiResult<List<User>>
    suspend fun deleteContact(contactId: Int): ApiResult<List<User>>
    suspend fun getUserContacts(contactId: Int): ApiResult<List<User>>
    fun findContact(contactId: Int): ContactItem?

}