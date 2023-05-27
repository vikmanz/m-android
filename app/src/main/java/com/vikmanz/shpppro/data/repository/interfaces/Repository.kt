package com.vikmanz.shpppro.data.repository.interfaces

import kotlinx.coroutines.flow.StateFlow
import java.net.URL

interface Repository<T> {

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

    fun generateRandomContactListItem(): T

    fun setFakeContacts()

    fun setPhoneContacts()

    fun getCurrentContactPhotoUrl(): URL

    fun getCurrentPhotoCounter(): Int

    fun incrementPhotoCounter()

    fun addContact(contact: T)

    fun addContact(contact: T, index: Int)

    fun deleteContact(contact: T)
    fun checkContact(index: Int): Unit?
    fun unCheckContact(index: Int): Unit?

    fun getContactPosition(contact: T): Int

    fun getContact(index: Int): T?

    fun findContact(id: Long): T?

    fun isContainsContact(contact: T): Boolean

}