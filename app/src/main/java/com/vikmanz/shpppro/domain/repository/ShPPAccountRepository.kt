package com.vikmanz.shpppro.domain.repository

import com.vikmanz.shpppro.common.model.Account
import com.vikmanz.shpppro.common.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import ua.digitalminds.fortrainerapp.data.result.ApiResult

interface ShPPAccountRepository {

    val account: Account

    suspend fun registerUser(email: String, password: String): ApiResult<Account>
    suspend fun authorizeUser(email: String, password: String): ApiResult<Account>
    suspend fun refreshToken(): ApiResult<Account>
    suspend fun getUser(): ApiResult<User>
    suspend fun editUser(
        name: String?,
        phone: String?,
        address: String?,
        career: String?,
        birthday:String?,
        facebook: String?,
        instagram: String?,
        twitter: String?,
        linkedin: String?
    ): ApiResult<User>

}