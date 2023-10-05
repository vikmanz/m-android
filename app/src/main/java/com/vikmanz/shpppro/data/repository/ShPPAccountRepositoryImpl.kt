package com.vikmanz.shpppro.data.repository

import com.vikmanz.shpppro.data.model.User
import com.vikmanz.shpppro.data.api.ShPPApi
import com.vikmanz.shpppro.data.dto.UserAuthorizeRequest
import com.vikmanz.shpppro.data.dto.UserEditRequest
import com.vikmanz.shpppro.data.dto.UserRegisterRequest
import com.vikmanz.shpppro.data.dto.toUser
import com.vikmanz.shpppro.data.result.ApiSafeCaller
import com.vikmanz.shpppro.data.holders.user_token.UserTokenHolder
import com.vikmanz.shpppro.domain.repository.ShPPAccountRepository
import ua.digitalminds.fortrainerapp.data.result.ApiResult
import javax.inject.Inject

//todo withContext(Dispatchers.IO) { ?
/**
 * Implementation of repository.
 * Main service to create contacts objects from information on from random.
 */
class ShPPAccountRepositoryImpl @Inject constructor(
    private val api: ShPPApi,
    private val apiSafeCaller: ApiSafeCaller,
    private val userTokenHandler: UserTokenHolder
) : ShPPAccountRepository {

    override suspend fun registerUser(
        email: String,
        password: String
    ): ApiResult<Boolean> =
        apiSafeCaller.safeApiCall {             // do call
            api.registerUser(
                UserRegisterRequest(
                    email = email,
                    password = password
                )
            )
        }.also {    // save credentials
            if (it is ApiResult.Success) {
                with(it.value.data) {
                    userTokenHandler.setUserData(
                        user = user,
                        accessToken = accessToken,
                        refreshToken = accessToken
                    )
                }
            }
        }.convertToBoolean()                    // convert result to boolean and return


    override suspend fun authorizeUser(
        email: String,
        password: String
    ): ApiResult<Boolean> =
        apiSafeCaller.safeApiCall {             // do call
            api.authorizeUser(
                UserAuthorizeRequest(
                    email = email,
                    password = password
                )
            )
        }.also {    // save credentials
            if (it is ApiResult.Success) {
                with(it.value.data) {
                    userTokenHandler.setUserData(
                        user = user,
                        accessToken = accessToken,
                        refreshToken = refreshToken
                    )
                }
            }
        }.convertToBoolean()                    // convert result to boolean and return


    override suspend fun refreshToken(): ApiResult<Boolean> =
        apiSafeCaller.safeApiCall {             // do call
            api.refreshToken(
                refreshToken = "Bearer ${userTokenHandler.refreshToken}"
            )
        }.also {    // save credentials
            if (it is ApiResult.Success) {
                with(it.value.data) {
                    userTokenHandler.setUserData(
                        accessToken = accessToken,
                        refreshToken = refreshToken
                    )
                }
            }
        }.convertToBoolean()                    // convert result to boolean and return


    override suspend fun getUser(): ApiResult<User> =
        apiSafeCaller.safeApiCall {
            api.getUser(
                token = userTokenHandler.accessToken,
                userId = userTokenHandler.user.id
            ).toUser()
        }

    override suspend fun editUser(
        name: String?,
        phone: String?,
        address: String?,
        career: String?,
        birthday: String?,
        facebook: String?,
        instagram: String?,
        twitter: String?,
        linkedin: String?
    ): ApiResult<User> =
        apiSafeCaller.safeApiCall {                     // do call
            api.editUser(
                token = userTokenHandler.accessToken,
                userId = userTokenHandler.user.id,
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
        }.also {                        // save new user
            if (it is ApiResult.Success) {
                userTokenHandler.setUserData(
                    user = it.value
                )
            }
        }

}