package com.vikmanz.shpppro.domain.repository

import com.vikmanz.shpppro.common.model.Account
import com.vikmanz.shpppro.common.model.User
import com.vikmanz.shpppro.common.result.UseCaseResult

interface ShPPRepositoryNet {

    suspend fun registerUser(email: String, password: String): UseCaseResult<Account>
    suspend fun authorizeUser(email: String, password: String): UseCaseResult<Account>
    suspend fun refreshToken(oldAccount: Account): UseCaseResult<Account>
    suspend fun getUser(token: String, userId: Int): UseCaseResult<User>
    suspend fun editUser(token: String, user: User): UseCaseResult<User>
    suspend fun getAllUsers(token: String, user: User): UseCaseResult<List<User>>
    suspend fun addContact(token: String, userId: Int, contactId: Int): UseCaseResult<List<User>>
    suspend fun deleteContact(token: String, userId: Int, contactId: Int): UseCaseResult<List<User>>
    suspend fun getUserContacts(token: String, userId: Int, contactId: Int): UseCaseResult<List<User>>

}