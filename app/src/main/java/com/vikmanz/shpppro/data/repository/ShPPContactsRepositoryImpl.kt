package com.vikmanz.shpppro.data.repository

import com.vikmanz.shpppro.data.model.ContactItem
import com.vikmanz.shpppro.data.model.User
import com.vikmanz.shpppro.data.api.ShPPApi
import com.vikmanz.shpppro.data.dto.ContactAddRequest
import com.vikmanz.shpppro.data.dto.toListOfContacts
import com.vikmanz.shpppro.data.dto.toListOfUsers
import com.vikmanz.shpppro.data.dto.toUser
import com.vikmanz.shpppro.data.result.ApiSafeCaller
import com.vikmanz.shpppro.data.user_token.UserTokenHandler
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
class ShPPContactsRepositoryImpl @Inject constructor(
    private val api: ShPPApi,
    private val apiSafeCaller: ApiSafeCaller,
    private val userTokenHandler: UserTokenHandler
) : ShPPContactsRepository {

    //This object is a wrapper. if we pass it a new object it will call emit
    private val _contactList = MutableStateFlow(listOf<ContactItem>())

    //this object sends out the immutable list
    override val contactList: StateFlow<List<ContactItem>> = _contactList.asStateFlow()

    private val multiselectList = ArrayList<ContactItem>()

    override suspend fun getAllUsers(): ApiResult<List<User>> =
        apiSafeCaller.safeApiCall {
            api.getAllUsers(
                token = userTokenHandler.accessToken,
            ).toListOfUsers()
        }

    override suspend fun addContact(
        contactId: Int
    ): ApiResult<List<User>> =
        apiSafeCaller.safeApiCall {
            api.addContact(
                token = userTokenHandler.accessToken,
                userId = userTokenHandler.user.id,
                body = ContactAddRequest(
                    contactId = contactId
                )
            ).toListOfContacts()
        }

    override suspend fun deleteContact(
        contactId: Int
    ): ApiResult<List<User>> =
        apiSafeCaller.safeApiCall {
            api.deleteContact(
                token = userTokenHandler.accessToken,
                userId = userTokenHandler.user.id,
                contactId = contactId
            ).toListOfContacts()
        }

    override suspend fun getUserContacts(): ApiResult<List<User>> =
        apiSafeCaller.safeApiCall {
            api.getUserContacts(
                token = userTokenHandler.accessToken,
                userId = userTokenHandler.user.id,
            ).toListOfContacts()
        }

    override suspend fun getContact (userId: Int): ApiResult<User> =
        apiSafeCaller.safeApiCall {
            api.getUser(
                token = userTokenHandler.accessToken,
                userId = userId
            ).toUser()
        }

    override fun findContact(contactId: Int): ContactItem {
        return contactList.value[contactId]
    }



}