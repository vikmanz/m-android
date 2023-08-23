package com.vikmanz.shpppro.data.repository

import com.vikmanz.shpppro.common.model.Account
import com.vikmanz.shpppro.common.model.ContactItem
import com.vikmanz.shpppro.common.model.User
import com.vikmanz.shpppro.data.api.ShPPApi
import com.vikmanz.shpppro.data.dto.ContactAddRequest
import com.vikmanz.shpppro.data.dto.UserAuthorizeRequest
import com.vikmanz.shpppro.data.dto.UserEditRequest
import com.vikmanz.shpppro.data.dto.UserRegisterRequest
import com.vikmanz.shpppro.data.dto.toAccount
import com.vikmanz.shpppro.data.dto.toListOfContacts
import com.vikmanz.shpppro.data.dto.toListOfUsers
import com.vikmanz.shpppro.data.dto.toUser
import com.vikmanz.shpppro.data.result.ApiSafeCaller
import com.vikmanz.shpppro.domain.repository.ShPPAccountRepository
import com.vikmanz.shpppro.domain.repository.ShPPContactsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import ua.digitalminds.fortrainerapp.data.result.ApiResult
import javax.inject.Inject

//todo withContext(Dispatchers.IO) { ?
/**
 * Implementation of repository.
 * Main service to create contacts objects from information on from random.
 */
class ShPPAccountRepositoryImpl @Inject constructor(
    private val api: ShPPApi,
    private val apiSafeCaller: ApiSafeCaller
) : ShPPAccountRepository {


    private val _account = MutableStateFlow(Account())
    override val account: Account get() = _account.value

    override suspend fun registerUser(email: String, password: String): ApiResult<Account> {
        val result = apiSafeCaller.safeApiCall {
            api.registerUser(
                UserRegisterRequest(
                    email = email,
                    password = password
                )
            ).toAccount()
        }
        if (result is ApiResult.Success) _account.value = result.value
        return result
    }


    override suspend fun authorizeUser(email: String, password: String): ApiResult<Account> {
        val result = apiSafeCaller.safeApiCall {
            api.authorizeUser(
                UserAuthorizeRequest(
                    email = email,
                    password = password
                )
            ).toAccount()
        }
        if (result is ApiResult.Success) _account.value = result.value
        return result
    }


    override suspend fun refreshToken(): ApiResult<Account> {
        val result = apiSafeCaller.safeApiCall {
            api.refreshToken(
                refreshToken = "Bearer ${account.refreshToken}"
            ).toAccount(account.user)
        }
        if (result is ApiResult.Success) _account.value = result.value
        return result
    }


    override suspend fun getUser(): ApiResult<User> =
        apiSafeCaller.safeApiCall {
            api.getUser(
                token = "Bearer ${account.accessToken}",
                userId = requireNotNull(account.user.id)
            ).toUser()
        }

    override suspend fun editUser(
        name: String?,
        phone: String?,
        address: String?,
        career: String?,
        birthday:String?,
        facebook: String?,
        instagram: String?,
        twitter: String?,
        linkedin: String?
    ): ApiResult<User> {
        val result = apiSafeCaller.safeApiCall {
            api.editUser(
                token = "Bearer ${account.accessToken}",
                userId = account.user.id,
                body = UserEditRequest(
                    name = name,
                    phone = phone,
                    address = address,
                    career = career,
                    birthday = birthday,
                    facebook = facebook,
                    instagram = instagram,
                    twitter = twitter,
                    linkedin = linkedin
                )
            ).toUser()
        }
        if (result is ApiResult.Success) _account.value = _account.value.copy(
            user = result.value
        )
        return result
    }

}