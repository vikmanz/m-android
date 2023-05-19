package com.vikmanz.shpppro.presentation.main.my_contacts_list

import androidx.lifecycle.MutableLiveData
import com.vikmanz.shpppro.presentation.base.BaseViewModel
import com.vikmanz.shpppro.App
import com.vikmanz.shpppro.data.contact_model.Contact
import com.vikmanz.shpppro.presentation.navigator.Navigator
import com.vikmanz.shpppro.presentation.main.contact_details.ContactDetailsFragment
import com.vikmanz.shpppro.presentation.utils.extensions.swapBoolean

// FakeData (true) or PhoneData (false) view first on myContacts
private const val FAKE_LIST_FIRST = true

/**
 * ViewModel for My Contacts Activity.
 */
class MyContactsListViewModel(
    private val navigator: Navigator,
    @Suppress("unused") private val customArgument: MyContactsListFragment.CustomArgument
) : BaseViewModel() {

    private val _contactsRepository = App.contactsRepository

    init {
        _contactsRepository.setFakeContacts()
    }

    /**
     * Create fake contact list and Flow to take it from outside.
     */
    val contactList = _contactsRepository.contactList

    /**
     * Variables to control swap between fake contacts and phone contacts lists.
     */
    val fakeListActivated = MutableLiveData(FAKE_LIST_FIRST)

    /**
     * Add new contact to list of contacts to concrete index.
     */
    fun addContactToPosition(contact: Contact, index: Int) {
        _contactsRepository.addContact(contact, index)
    }

    /**
     * Delete contact from list of contacts.
     */
    fun deleteContact(contact: Contact) {
        _contactsRepository.deleteContact(contact)
    }

    /**
     * Get contact position in list of contacts.
     */
    fun getContactPosition(contact: Contact) : Int {
        return _contactsRepository.getContactPosition(contact)
    }

    /**
     * Get contact from list via index.
     */
    fun getContact(index: Int) : Contact? {
        return _contactsRepository.getContact(index)
    }

    /**
     * Get contact from list via index.
     */
    fun isContainsContact(contact: Contact) : Boolean {
        return _contactsRepository.isContainsContact(contact)
    }

    /**
     * Change contact list to fake contacts list.
     */
    fun getContactsList() {
        if (fakeListActivated.value == true){
            _contactsRepository.setPhoneContacts()
        } else {
            _contactsRepository.setFakeContacts()
        }
        fakeListActivated.swapBoolean()
    }

    fun onContactPressed(contactID: Long) {
        navigator.launchContactDetails(ContactDetailsFragment.CustomArgument(contactID))
    }

    fun onButtonBackPressed() {
        navigator.goBack()
    }
}