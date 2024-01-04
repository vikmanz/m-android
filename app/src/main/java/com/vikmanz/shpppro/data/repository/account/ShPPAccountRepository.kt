package com.vikmanz.shpppro.data.repository.account

import com.vikmanz.shpppro.data.model.User
import ua.digitalminds.fortrainerapp.data.result.ApiResult

interface ShPPAccountRepository {

    suspend fun registerUser(email: String, password: String): ApiResult<Boolean>
    suspend fun authorizeUser(email: String, password: String): ApiResult<Boolean>
    suspend fun refreshToken(): ApiResult<Boolean>
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