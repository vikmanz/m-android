package com.vikmanz.shpppro.data.repository

import com.vikmanz.shpppro.common.model.ContactItem
import com.vikmanz.shpppro.common.model.User
import com.vikmanz.shpppro.data.api.ShPPApi
import com.vikmanz.shpppro.data.dto.ContactAddRequest
import com.vikmanz.shpppro.data.dto.toListOfContacts
import com.vikmanz.shpppro.data.dto.toListOfUsers
import com.vikmanz.shpppro.data.result.ApiSafeCaller
import com.vikmanz.shpppro.domain.repository.ShPPAccountRepository
import com.vikmanz.shpppro.domain.repository.ShPPContactsRepository
import com.vikmanz.shpppro.domain.usecases.account.GetAccountUseCase
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
    private val apiSafeCaller: ApiSafeCaller
) : ShPPContactsRepository {

    //This object is a wrapper. if we pass it a new object it will call emit
    private val _contactList = MutableStateFlow(listOf<ContactItem>())

    //this object sends out the immutable list
    override val contactList: StateFlow<List<ContactItem>> = _contactList.asStateFlow()

    private val multiselectList = ArrayList<ContactItem>()

    override suspend fun getAllUsers(token: String, user: User): ApiResult<List<User>> {
        val result = apiSafeCaller.safeApiCall {
            api.getAllUsers(
                token = "Bearer $token",
                //token = "Bearer ",
            ).toListOfUsers()
        }
        if (result is ApiResult.Success) _contactList.value = result.value.map { ContactItem(it) }
        return result
    }


    override suspend fun addContact(
        token: String,
        userId: Int,
        contactId: Int
    ): ApiResult<List<User>> = apiSafeCaller.safeApiCall {
        api.addContact(
            token = "Bearer $token",
            //token = "Bearer ",
            userId = userId,
            //userId = 308,
            body = ContactAddRequest(
                contactId = contactId
            )
        ).toListOfContacts()
    }


    override suspend fun deleteContact(
        token: String,
        userId: Int,
        contactId: Int
    ): ApiResult<List<User>> = apiSafeCaller.safeApiCall {
        api.deleteContact(
            token = "Bearer $token",
            //token = "Bearer ",
            userId = userId,
            //userId = 308,
            contactId = contactId
        ).toListOfContacts()
    }

    override suspend fun getUserContacts(
        token: String,
        userId: Int,
        contactId: Int
    ): ApiResult<List<User>> = apiSafeCaller.safeApiCall {
        api.getUserContacts(
            token = "Bearer $token",
            //token = "Bearer ",
            userId = userId,
            //userId = 308,
        ).toListOfContacts()
    }

    override fun findContact(contactId: Int): ContactItem? {
        return contactList.value[contactId]
    }


}