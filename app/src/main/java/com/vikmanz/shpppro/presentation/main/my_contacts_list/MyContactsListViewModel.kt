package com.vikmanz.shpppro.presentation.main.my_contacts_list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vikmanz.shpppro.data.contact_model.Contact
import com.vikmanz.shpppro.data.repository.interfaces.Repository
import com.vikmanz.shpppro.presentation.navigator.Navigator
import com.vikmanz.shpppro.presentation.utils.extensions.swapBoolean
import javax.inject.Inject

// FakeData (true) or PhoneData (false) view first on myContacts
private const val FAKE_LIST_FIRST = true

/**
 * ViewModel for My Contacts Activity.
 */
class MyContactsListViewModel(
    private val navigator: Navigator
) : ViewModel() {

    @Inject
    lateinit var contactsRepository: Repository<Contact>

    init {
        contactsRepository.setFakeContacts()
    }

    /**
     * Create fake contact list and Flow to take it from outside.
     */
    val contactList = contactsRepository.contactList

    /**
     * Variables to control swap between fake contacts and phone contacts lists.
     */
    val fakeListActivated = MutableLiveData(FAKE_LIST_FIRST)

    /**
     * Add new contact to list of contacts to concrete index.
     */
    fun addContactToPosition(contact: Contact, index: Int) {
        contactsRepository.addContact(contact, index)
    }

    /**
     * Delete contact from list of contacts.
     */
    fun deleteContact(contact: Contact) {
        contactsRepository.deleteContact(contact)
    }

    /**
     * Get contact position in list of contacts.
     */
    fun getContactPosition(contact: Contact) : Int {
        return contactsRepository.getContactPosition(contact)
    }

    /**
     * Get contact from list via index.
     */
    fun getContact(index: Int) : Contact? {
        return contactsRepository.getContact(index)
    }

    /**
     * Get contact from list via index.
     */
    fun isContainsContact(contact: Contact) : Boolean {
        return contactsRepository.isContainsContact(contact)
    }

    /**
     * Change contact list to fake contacts list.
     */
    fun getContactsList() {
        if (fakeListActivated.value == true){
            contactsRepository.setPhoneContacts()
        } else {
            contactsRepository.setFakeContacts()
        }
        fakeListActivated.swapBoolean()
    }

    fun onContactPressed(contactID: Long) {
        navigator.launchContactDetails()
    }

    fun onButtonBackPressed() {
        navigator.goBack()
    }
}