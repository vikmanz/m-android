package com.vikmanz.shpppro.data.holders.user_contact_list

import com.vikmanz.shpppro.data.model.contact_item.ContactItem
import kotlinx.coroutines.flow.StateFlow

interface ShPPUserContactsListHolder {

    val contactList: StateFlow<List<ContactItem>>

    fun clearMultiselect()

    fun findContact(contactId: Int): ContactItem?

    fun changeContactItemCheckedState(contactItemId: Int): Boolean
    fun updateContactList(newContactList: List<ContactItem>)
    fun setLoadingStatus(contactItemId: Int, isLoading: Boolean)

    fun setErrorStatus(contactItemId: Int)
}