package com.vikmanz.shpppro.ui

import androidx.lifecycle.*
import com.vikmanz.shpppro.constants.Constants.FAKE_FIRST
import com.vikmanz.shpppro.data.contactModel.Contact
import com.vikmanz.shpppro.data.ContactsReposetory

/**
 * ViewModel for My Contacts Activity.
 */
class MyContactsViewModel(
    private val _contactsReposetory: ContactsReposetory
) : ViewModel() {

    /**
     * Create fake contact list and Flow to take it from outside.
     */
    val contactList = _contactsReposetory.contactList

    /**
     * Variables to control swap between fake contacts and phone contacts lists.
     */
    var phoneListActivated = !FAKE_FIRST
    var phoneListChangedToFake = FAKE_FIRST

    /**
     * Add new contact to list of contacts to concrete index.
     */
    fun addContactToPosition(contact: Contact, index: Int) {
        _contactsReposetory.addContact(contact, index)
    }

    /**
     * Delete contact from list of contacts.
     */
    fun deleteContact(contact: Contact) {
        _contactsReposetory.deleteContact(contact)
    }

    /**
     * Get contact position in list of contacts.
     */
    fun getContactPosition(contact: Contact) : Int {
        return _contactsReposetory.getContactPosition(contact)
    }

    /**
     * Get contact from list via index.
     */
    fun getContact(index: Int) : Contact {
        return _contactsReposetory.getContact(index)
    }

    /**
     * Change contact list to fake contacts list.
     */
    fun getFakeContacts() {
        _contactsReposetory.setFakeContacts()
        phoneListChangedToFake = FAKE_FIRST
    }

    /**
     * Change contact list to phone contacts list.
     */
    fun setPhoneContactList() {
        _contactsReposetory.setPhoneContacts()
        phoneListActivated = FAKE_FIRST
        phoneListChangedToFake = !FAKE_FIRST
    }
}