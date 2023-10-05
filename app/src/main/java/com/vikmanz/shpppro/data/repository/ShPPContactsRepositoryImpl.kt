package com.vikmanz.shpppro.data.repository

import com.vikmanz.shpppro.data.model.ContactItem
import com.vikmanz.shpppro.data.model.User
import com.vikmanz.shpppro.data.api.ShPPApi
import com.vikmanz.shpppro.data.dto.ContactAddRequest
import com.vikmanz.shpppro.data.dto.toListOfContactItems
import com.vikmanz.shpppro.data.dto.toListOfUsers
import com.vikmanz.shpppro.data.dto.toUser
import com.vikmanz.shpppro.data.holders.user_contact_list.ContactsListHolder
import com.vikmanz.shpppro.data.result.ApiSafeCaller
import com.vikmanz.shpppro.data.holders.user_token.UserTokenHolder
import com.vikmanz.shpppro.domain.repository.ShPPContactsRepository
import com.vikmanz.shpppro.domain.repository.ShPPUserContactsList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
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
    private val userTokenHolder: UserTokenHolder,
    private val contactListHolder: ContactsListHolder,
) : ShPPContactsRepository {

    override suspend fun getAllUsers(): ApiResult<List<User>> =
        apiSafeCaller.safeApiCall {
            api.getAllUsers(
                token = userTokenHolder.accessToken,
            ).toListOfUsers()
        }

    override suspend fun addContact(
        contactId: Int
    ): ApiResult<List<ContactItem>> =
        apiSafeCaller.safeApiCall {
            api.addContact(
                token = userTokenHolder.accessToken,
                userId = userTokenHolder.user.id,
                body = ContactAddRequest(
                    contactId = contactId
                )
            ).toListOfContactItems().also {
                println()
                contactListHolder.updateContactList(it)
            }
        }

    override suspend fun deleteContact(
        contactId: Int
    ): ApiResult<List<ContactItem>> =
        apiSafeCaller.safeApiCall {
            api.deleteContact(
                token = userTokenHolder.accessToken,
                userId = userTokenHolder.user.id,
                contactId = contactId
            ).toListOfContactItems().also {
                println()
                contactListHolder.updateContactList(it)
            }
        }

    override suspend fun getUserContacts(): ApiResult<List<ContactItem>> =
        apiSafeCaller.safeApiCall {
            api.getUserContacts(
                token = userTokenHolder.accessToken,
                userId = userTokenHolder.user.id,
            ).toListOfContactItems().also {
                println()
                contactListHolder.updateContactList(it)
            }
        }

    override suspend fun getContact(userId: Int): ApiResult<User> =
        apiSafeCaller.safeApiCall {
            api.getUser(
                token = userTokenHolder.accessToken,
                userId = userId
            ).toUser()
        }


}