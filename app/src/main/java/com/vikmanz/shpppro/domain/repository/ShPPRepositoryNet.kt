package com.vikmanz.shpppro.domain.repository

import com.vikmanz.shpppro.common.model.Account
import com.vikmanz.shpppro.common.model.User
import ua.digitalminds.fortrainerapp.data.result.ApiResult

interface ShPPRepositoryNet {

    suspend fun registerUser(email: String, password: String): ApiResult<Account>
    suspend fun authorizeUser(email: String, password: String): ApiResult<Account>
    suspend fun refreshToken(oldAccount: Account): ApiResult<Account>
    suspend fun getUser(token: String, userId: Int): ApiResult<User>
    suspend fun editUser(token: String, user: User): ApiResult<User>
    suspend fun getAllUsers(token: String, user: User): ApiResult<List<User>>
    suspend fun addContact(token: String, userId: Int, contactId: Int): ApiResult<List<User>>
    suspend fun deleteContact(token: String, userId: Int, contactId: Int): ApiResult<List<User>>
    suspend fun getUserContacts(token: String, userId: Int, contactId: Int): ApiResult<List<User>>

}