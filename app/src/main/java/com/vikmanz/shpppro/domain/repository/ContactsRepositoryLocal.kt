package com.vikmanz.shpppro.domain.repository

import kotlinx.coroutines.flow.StateFlow
import java.net.URL

interface ContactsRepositoryLocal<T> {

    val contactList: StateFlow<List<T>>

    fun createContact(
        contactPhotoLink: Any,
        photoIndex: Int,
        name: String,
        career: String,
        email: String,
        phone: String,
        address: String,
        birthday: String
    ): T

    fun generateRandomContact(): T

    suspend fun setFakeContacts()

    fun setPhoneContacts()

    fun getCurrentContactPhotoUrl(): URL

    fun getCurrentPhotoCounter(): Int

    fun incrementPhotoCounter()

    fun addContact(contact: T)

    fun addContact(contact: T, index: Int)

    fun deleteContact(contact: T)

    fun getContactPosition(contact: T): Int

    fun getContact(index: Int): T?

    fun findContact(contactId: Long): T?

    fun isContainsContact(contact: T): Boolean

    fun checkIsMultiselect(): Boolean
    fun deleteMultipleContacts()

    fun toggleContactSelectionState(contact: T)
    fun checkMultiselectState(): Boolean
    fun clearMultiselect()


}