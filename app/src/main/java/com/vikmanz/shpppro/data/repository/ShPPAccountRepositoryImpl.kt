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
    override val account: StateFlow<Account> = _account.asStateFlow()

    override suspend fun registerUser(email: String, password: String): ApiResult<Account> =
        apiSafeCaller.safeApiCall {
            api.registerUser(
                UserRegisterRequest(
                    email = email,
                    password = password
                )
            ).toAccount()
        }


    override suspend fun authorizeUser(email: String, password: String): ApiResult<Account> =
        apiSafeCaller.safeApiCall {
            api.authorizeUser(
                UserAuthorizeRequest(
                    email = email,
                    password = password
                )
            ).toAccount()
        }

    override suspend fun refreshToken(oldAccount: Account): ApiResult<Account> =
        apiSafeCaller.safeApiCall {
            api.refreshToken(
                refreshToken = "Bearer ${oldAccount.refreshToken}"
            ).toAccount(oldAccount.user)
        }


    override suspend fun getUser(token: String, userId: Int): ApiResult<User> =
        apiSafeCaller.safeApiCall {
            api.getUser(
                token = "Bearer $token",
                userId = userId
            ).toUser()
        }

    override suspend fun editUser(token: String, user: User): ApiResult<User> =
        apiSafeCaller.safeApiCall {
            api.editUser(
                token = "Bearer $token",
                userId = requireNotNull(user.id),
                body = UserEditRequest(
                    name = user.name,
                    phone = user.phone,
                    address = user.address,
                    career = user.career,
                    birthday = user.birthday,
                    facebook = user.facebook,
                    instagram = user.instagram,
                    twitter = user.twitter,
                    linkedin = user.linkedin
                )
            ).toUser()
        }

}