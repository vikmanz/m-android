package com.vikmanz.shpppro.data.repository

import com.vikmanz.shpppro.common.extensions.log
import com.vikmanz.shpppro.common.model.Account
import com.vikmanz.shpppro.common.model.ContactItem
import com.vikmanz.shpppro.common.model.User
import com.vikmanz.shpppro.common.result.UseCaseResult
import com.vikmanz.shpppro.data.api.ShPPApi
import com.vikmanz.shpppro.data.dto.ContactAddRequest
import com.vikmanz.shpppro.data.dto.UserAuthorizeRequest
import com.vikmanz.shpppro.data.dto.UserEditRequest
import com.vikmanz.shpppro.data.dto.UserRegisterRequest
import com.vikmanz.shpppro.data.dto.toAccount
import com.vikmanz.shpppro.data.dto.toListOfContacts
import com.vikmanz.shpppro.data.dto.toListOfUsers
import com.vikmanz.shpppro.data.dto.toUser
import com.vikmanz.shpppro.domain.repository.ShPPRepositoryNet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Implementation of repository.
 * Main service to create contacts objects from information on from random.
 */
class ShPPRepositoryNetImpl @Inject constructor(
    private val api: ShPPApi
) : ShPPRepositoryNet {

    //This object is a wrapper. if we pass it a new object it will call emit
    private val _contactList = MutableStateFlow(listOf<ContactItem>())

    //this object sends out the immutable list
    val contactList: StateFlow<List<ContactItem>> = _contactList.asStateFlow()

    private val multiselectList = ArrayList<ContactItem>()


    override suspend fun registerUser(email: String, password: String): UseCaseResult<Account> =
        withContext(Dispatchers.IO) {
            return@withContext try {

                val account = api.registerUser(
                    UserRegisterRequest(
                        email = email,
                        password = password
                    )
                ).toAccount()
                UseCaseResult.Success(account)
            } catch (e: Exception) {
                log("ERROR! ShPPRepositoryNetImpl -> registerUser()")
                UseCaseResult.Error(e.localizedMessage ?: "unknown error")
            }
        }


    override suspend fun authorizeUser(email: String, password: String): UseCaseResult<Account> =
        withContext(Dispatchers.IO) {
            return@withContext try {

                val response = api.authorizeUser(
                    UserAuthorizeRequest(
                        email = email,
                        password = password
                    )
                ).toAccount()
                UseCaseResult.Success(response)

            } catch (e: Exception) {
                log("ERROR! ShPPRepositoryNetImpl -> registerUser()")
                UseCaseResult.Error(e.localizedMessage ?: "unknown error")
            }
        }

    override suspend fun refreshToken(oldAccount: Account): UseCaseResult<Account> =
        withContext(Dispatchers.IO) {
            return@withContext try {

                val response = api.refreshToken(
                    refreshToken = "Bearer ${oldAccount.refreshToken}"
                ).toAccount(oldAccount.user)
                UseCaseResult.Success(response)

            } catch (e: Exception) {
                log("ERROR! ShPPRepositoryNetImpl -> registerUser()")
                UseCaseResult.Error(e.localizedMessage ?: "unknown error")
            }
        }


    override suspend fun getUser(token: String, userId: Int): UseCaseResult<User> =
        withContext(Dispatchers.IO) {
            return@withContext try {

                val response = api.getUser(
                    token = "Bearer $token",
                    userId = userId
                ).toUser()
                UseCaseResult.Success(response)

            } catch (e: Exception) {
                log("ERROR! ShPPRepositoryNetImpl -> registerUser()")
                UseCaseResult.Error(e.localizedMessage ?: "unknown error")
            }
        }

    override suspend fun editUser(token: String, user: User): UseCaseResult<User> =
        withContext(Dispatchers.IO) {
            return@withContext try {

                val response = api.editUser(
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
                UseCaseResult.Success(response)

            } catch (e: Exception) {
                log("ERROR! ShPPRepositoryNetImpl -> registerUser()")
                UseCaseResult.Error(e.localizedMessage ?: "unknown error")
            }
        }

    override suspend fun getAllUsers(token: String, user: User): UseCaseResult<List<User>> =
        withContext(Dispatchers.IO) {
            return@withContext try {

                val response = api.getAllUsers(
                    token = "Bearer $token",
                ).toListOfUsers()
                UseCaseResult.Success(response)

            } catch (e: Exception) {
                log("ERROR! ShPPRepositoryNetImpl -> registerUser()")
                UseCaseResult.Error(e.localizedMessage ?: "unknown error")
            }
        }

    override suspend fun addContact(
        token: String,
        userId: Int,
        contactId: Int
    ): UseCaseResult<List<User>> =
        withContext(Dispatchers.IO) {
            return@withContext try {

                val response = api.addContact(
                    token = "Bearer $token",
                    userId = userId,
                    body = ContactAddRequest(
                        contactId = contactId
                    )
                ).toListOfContacts()
                UseCaseResult.Success(response)

            } catch (e: Exception) {
                log("ERROR! ShPPRepositoryNetImpl -> registerUser()")
                UseCaseResult.Error(e.localizedMessage ?: "unknown error")
            }
        }

    override suspend fun deleteContact(
        token: String,
        userId: Int,
        contactId: Int
    ): UseCaseResult<List<User>> =
        withContext(Dispatchers.IO) {
            return@withContext try {

                val response = api.deleteContact(
                    token = "Bearer $token",
                    userId = userId,
                    contactId = contactId
                ).toListOfContacts()
                UseCaseResult.Success(response)

            } catch (e: Exception) {
                log("ERROR! ShPPRepositoryNetImpl -> registerUser()")
                UseCaseResult.Error(e.localizedMessage ?: "unknown error")
            }
        }

    override suspend fun getUserContacts(
        token: String,
        userId: Int,
        contactId: Int
    ): UseCaseResult<List<User>> =
        withContext(Dispatchers.IO) {
            return@withContext try {

                val response = api.getUserContacts(
                    token = "Bearer $token",
                    userId = userId,
                ).toListOfContacts()
                UseCaseResult.Success(response)

            } catch (e: Exception) {
                log("ERROR! ShPPRepositoryNetImpl -> registerUser()")
                UseCaseResult.Error(e.localizedMessage ?: "unknown error")
            }
        }


}