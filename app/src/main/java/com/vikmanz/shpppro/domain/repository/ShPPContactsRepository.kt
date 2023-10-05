package com.vikmanz.shpppro.domain.repository

import com.vikmanz.shpppro.data.model.ContactItem
import com.vikmanz.shpppro.data.model.User
import kotlinx.coroutines.flow.StateFlow
import ua.digitalminds.fortrainerapp.data.result.ApiResult

interface ShPPContactsRepository {
    suspend fun getAllUsers(): ApiResult<List<User>>
    suspend fun addContact(contactId: Int): ApiResult<List<ContactItem>>
    suspend fun deleteContact(contactId: Int): ApiResult<List<ContactItem>>
    suspend fun getUserContacts(): ApiResult<List<ContactItem>>
    suspend fun getContact(userId: Int): ApiResult<User>

}