package com.vikmanz.shpppro.domain.repository

import com.vikmanz.shpppro.common.model.ContactItem
import kotlinx.coroutines.flow.StateFlow
import java.net.URL

interface ContactsRepositoryLocal {

    val contactList: StateFlow<List<ContactItem>>

    fun createContact(
        contactPhotoLink: Any,
        photoIndex: Int,
        name: String,
        career: String,
        email: String,
        phone: String,
        address: String,
        birthday: String
    ): ContactItem

    fun generateRandomContact(): ContactItem

    suspend fun setFakeContacts()

    fun setPhoneContacts()

    fun getCurrentContactPhotoUrl(): URL

    fun getCurrentPhotoCounter(): Int

    fun incrementPhotoCounter()

    fun addContact(contact: ContactItem)

    fun addContact(contact: ContactItem, index: Int)

    fun deleteContact(contact: ContactItem)

    fun getContactPosition(contact: ContactItem): Int

    fun getContact(index: Int): ContactItem?

    fun findContact(contactId: Int): ContactItem?

    fun isContainsContact(contact: ContactItem): Boolean

    fun checkIsMultiselect(): Boolean
    fun deleteMultipleContacts()

    fun toggleContactSelectionState(contact: ContactItem)
    fun checkMultiselectState(): Boolean
    fun clearMultiselect()


}