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
import java.net.URL
import javax.inject.Inject

//todo withContext(Dispatchers.IO) { ?
/**
 * Implementation of repository.
 * Main service to create contacts objects from information on from random.
 */
class ShPPContactsRepositoryImpl @Inject constructor(
    private val api: ShPPApi,
    private val apiSafeCaller: ApiSafeCaller,
    private val accRepository: ShPPAccountRepository
) : ShPPContactsRepository {

    //This object is a wrapper. if we pass it a new object it will call emit
    private val _contactList = MutableStateFlow(listOf<ContactItem>())

    //this object sends out the immutable list
    override val contactList: StateFlow<List<ContactItem>> = _contactList.asStateFlow()

    private val multiselectList = ArrayList<ContactItem>()

    override suspend fun getAllUsers(user: User): ApiResult<List<User>> {
        val result = apiSafeCaller.safeApiCall {
            api.getAllUsers(
                token = "Bearer ${accRepository.account.accessToken}",
            ).toListOfUsers()
        }
        if (result is ApiResult.Success) _contactList.value = result.value.map { ContactItem(it) }
        return result
    }


    override suspend fun addContact(
        contactId: Int
    ): ApiResult<List<User>> = apiSafeCaller.safeApiCall {
        api.addContact(
            token = "Bearer ${accRepository.account.accessToken}",
            userId = accRepository.account.user.id,
            body = ContactAddRequest(
                contactId = contactId
            )
        ).toListOfContacts()
    }


    override suspend fun deleteContact(
        contactId: Int
    ): ApiResult<List<User>> = apiSafeCaller.safeApiCall {
        api.deleteContact(
            token = "Bearer ${accRepository.account.accessToken}",
            userId = accRepository.account.user.id,
            contactId = contactId
        ).toListOfContacts()

    }

    override suspend fun getUserContacts(
        contactId: Int
    ): ApiResult<List<User>> = apiSafeCaller.safeApiCall {
        api.getUserContacts(
            token = "Bearer ${accRepository.account.accessToken}",
            userId = accRepository.account.user.id,
        ).toListOfContacts()
    }

    override fun findContact(contactId: Int): ContactItem? {
        return contactList.value[contactId]
    }


}