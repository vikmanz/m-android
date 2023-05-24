package com.vikmanz.shpppro.ui.main.my_contacts_list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.vikmanz.shpppro.base.BaseViewModel
import com.vikmanz.shpppro.data.contact_model.Contact
import com.vikmanz.shpppro.data.repository.interfaces.Repository
import com.vikmanz.shpppro.utilits.extensions.swapBoolean
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

// FakeData (true) or PhoneData (false) view first on myContacts
private const val FAKE_LIST_FIRST = true

/**
 * ViewModel for My Contacts Activity.
 */
@HiltViewModel
class MyContactsListViewModel @Inject constructor(
    contactsRepository: Repository<Contact>
) : BaseViewModel() {

    private val _repository = contactsRepository

    init {
        _repository.setFakeContacts()
    }

    /**
     * Create fake contact list and Flow to take it from outside.
     */
    val contactList = _repository.contactList

    /**
     * Variables to control swap between fake contacts and phone contacts lists.
     */
    val fakeListActivated = MutableLiveData(FAKE_LIST_FIRST)

    /**
     * Add new contact to list of contacts to concrete index.
     */
    fun addContactToPosition(contact: Contact, index: Int) {
        _repository.addContact(contact, index)
    }

    /**
     * Delete contact from list of contacts.
     */
    fun deleteContact(contact: Contact) {
        _repository.deleteContact(contact)
    }

    /**
     * Get contact position in list of contacts.
     */
    fun getContactPosition(contact: Contact) : Int {
        return _repository.getContactPosition(contact)
    }

    /**
     * Get contact from list via index.
     */
    fun getContact(index: Int) : Contact? {
        return _repository.getContact(index)
    }

    /**
     * Get contact from list via index.
     */
    fun isContainsContact(contact: Contact) : Boolean {
        return _repository.isContainsContact(contact)
    }

    /**
     * Change contact list to fake contacts list.
     */
    fun getContactsList() {
        viewModelScope.launch {
            if (fakeListActivated.value == true){
                _repository.setPhoneContacts()
            } else {
                _repository.setFakeContacts()
            }
            fakeListActivated.swapBoolean()
        }
    }

    fun onContactPressed(contactID: Long) {
        navigate(MyContactsListFragmentDirections.startContactDetails(contactID))
    }

    fun onButtonBackPressed() {
        navigateBack()
    }
}