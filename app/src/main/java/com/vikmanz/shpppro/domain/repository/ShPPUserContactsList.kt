package com.vikmanz.shpppro.domain.repository

import com.vikmanz.shpppro.data.model.ContactItem
import com.vikmanz.shpppro.data.model.User
import kotlinx.coroutines.flow.StateFlow
import ua.digitalminds.fortrainerapp.data.result.ApiResult

interface ShPPUserContactsList {

    val contactList: StateFlow<List<ContactItem>>

    fun clearMultiselect()

    fun findContact(contactId: Int): ContactItem?

    fun changeContactItemCheckedState(contactItemId: Int): Boolean
    fun updateContactList(newContactList: List<ContactItem>)
    fun setLoadingStatus(contactItemId: Int, isLoading: Boolean)
}