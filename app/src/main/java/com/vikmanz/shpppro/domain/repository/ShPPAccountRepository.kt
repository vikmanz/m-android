package com.vikmanz.shpppro.domain.repository

import com.vikmanz.shpppro.common.model.Account
import com.vikmanz.shpppro.common.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import ua.digitalminds.fortrainerapp.data.result.ApiResult

interface ShPPAccountRepository {

    val account: StateFlow<Account>

    suspend fun registerUser(email: String, password: String): ApiResult<Account>
    suspend fun authorizeUser(email: String, password: String): ApiResult<Account>
    suspend fun refreshToken(oldAccount: Account): ApiResult<Account>
    suspend fun getUser(token: String, userId: Int): ApiResult<User>
    suspend fun editUser(token: String, user: User): ApiResult<User>

}