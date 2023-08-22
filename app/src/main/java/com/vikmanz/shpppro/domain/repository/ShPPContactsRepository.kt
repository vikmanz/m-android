package com.vikmanz.shpppro.domain.repository

import com.vikmanz.shpppro.common.model.User
import ua.digitalminds.fortrainerapp.data.result.ApiResult

interface ShPPContactsRepository {

    suspend fun getAllUsers(token: String, user: User): ApiResult<List<User>>
    suspend fun addContact(token: String, userId: Int, contactId: Int): ApiResult<List<User>>
    suspend fun deleteContact(token: String, userId: Int, contactId: Int): ApiResult<List<User>>
    suspend fun getUserContacts(token: String, userId: Int, contactId: Int): ApiResult<List<User>>

}