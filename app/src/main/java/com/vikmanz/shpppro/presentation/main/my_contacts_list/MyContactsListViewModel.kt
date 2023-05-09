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

    private val _contactsReposetory = App.contactsRepository

    /**
     * Create fake contact list and Flow to take it from outside.
     */
    val contactList = _contactsReposetory.contactList

    /**
     * Variables to control swap between fake contacts and phone contacts lists.
     */
    val fakeListActivated = MutableLiveData(FAKE_LIST_FIRST)

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
    fun getContact(index: Int) : Contact? {
        return _contactsReposetory.getContact(index)
    }

    /**
     * Get contact from list via index.
     */
    fun isContainsContact(contact: Contact) : Boolean {
        return _contactsReposetory.isContainsContact(contact)
    }

    /**
     * Change contact list to fake contacts list.
     */
    fun getContactsList() {
        if (fakeListActivated.value == true){
            _contactsReposetory.setPhoneContacts()
        } else {
            _contactsReposetory.setFakeContacts()
        }
        fakeListActivated.swapBoolean()
    }

    fun onContactPressed(contactID: Long) {
        //navigator.launchContactDetails(ContactDetailsFragment.CustomArgument("to_ContactsDetail_args", contactID))
        navigator.launchContactDetails(ContactDetailsFragment.CustomArgument(contactID))
    }

    fun onButtonBackPressed() {
        navigator.goBack()
    }
}