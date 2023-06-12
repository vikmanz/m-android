package com.vikmanz.shpppro.data.repository.interfaces

import kotlinx.coroutines.flow.StateFlow
import java.net.URL

interface ContactsRepository<T> {

    val contactList: StateFlow<List<T>>

    fun createContactListItem(
        contactPhotoLink: Any,
        photoIndex: Int,
        name: String,
        career: String,
        email: String,
        phone: String,
        address: String,
        birthday: String
    ): T

    fun generateRandomContactItem(): T

    fun setFakeContacts()

    fun setPhoneContacts()

    fun getCurrentContactPhotoUrl(): URL

    fun getCurrentPhotoCounter(): Int

    fun incrementPhotoCounter()

    fun addContactItem(contactListItem: T)

    fun addContactItem(contactListItem: T, index: Int)

    fun deleteContactItem(contactListItem: T)

    fun getContactItemPosition(contactListItem: T): Int

    fun getContactItem(index: Int): T?

    fun findContactItem(id: Long): T?

    fun isContainsContactItem(contactListItem: T): Boolean

    fun checkContactInMultiselect(contactListItem: T): Boolean
    fun deleteMultipleContactItems()

    fun toggleIsSelected(contactListItem: T)
    fun clearMultiselect()

}