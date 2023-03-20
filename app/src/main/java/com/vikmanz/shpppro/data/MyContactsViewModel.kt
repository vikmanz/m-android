package com.vikmanz.shpppro.data

import androidx.lifecycle.ViewModel
import com.vikmanz.shpppro.constants.Constants.FAKE_FIRST
import com.vikmanz.shpppro.data.contactModel.Contact
import com.vikmanz.shpppro.data.contactModel.ContactsService
import com.vikmanz.shpppro.utilits.log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * ViewModel for My Contacts Activity.
 */
class MyContactsViewModel : ViewModel() {

    /**
     * Create contacts service variable.
     */
    private val _contactService = ContactsService()

    /**
     * Create fake contact list.
     */
    private val _contactList = MutableStateFlow(_contactService.createFakeContacts())

    /**
     * Flow to take it from outside.
     */
    var contactList: StateFlow<List<Contact>> = _contactList

    /**
     * Variables to control swap between fake contacts and phone contacts lists.
     */
    var phoneListActivated = !FAKE_FIRST
    var phoneListChangedToFake = FAKE_FIRST

    /**
     * Add new contact to list of contacts to end of list.
     */
    fun addContact(contact: Contact) {
        addContact(contact, _contactList.value.size)
        log("New contact created! id:${contact.contactId}, name:${contact.contactName}, contact img counter: ${contact.contactPhotoIndex}.")
    }

    /**
     * Add new contact to list of contacts to concrete index.
     */
    fun addContact(contact: Contact, index: Int) {
        _contactList.value = _contactList.value.toMutableList().apply { add(index, contact) }
    }

    /**
     * Delete contact from list of contacts.
     */
    fun deleteContact(contact: Contact) {
        _contactList.value = _contactList.value.toMutableList().apply { remove(contact) }
    }

    /**
     * Get contact position in list of contacts.
     */
    fun getContactPosition(contact: Contact) : Int{
        return _contactList.value.indexOf(contact)
    }

    /**
     * Get contact from list via index.
     */
    fun getContact(index: Int) : Contact {
        return _contactList.value[index]
    }

    /**
     * Change contact list to fake contacts list.
     */
    fun getFakeContacts() {
        _contactList.value = _contactService.createFakeContacts()
        phoneListChangedToFake = FAKE_FIRST
    }

    /**
     * Change contact list to phone contacts list.
     */
    fun setPhoneContactList() {
        _contactList.value = _contactService.createContactListFromPhonebookInfo()
        phoneListActivated = FAKE_FIRST
        phoneListChangedToFake = !FAKE_FIRST
    }
}