package com.vikmanz.shpppro.ui.main.main_fragment.my_contacts_list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.vikmanz.shpppro.data.model.Contact
import com.vikmanz.shpppro.data.model.ContactListItemState
import com.vikmanz.shpppro.data.repository.interfaces.ContactsRepository
import com.vikmanz.shpppro.ui.base.BaseViewModel
import com.vikmanz.shpppro.ui.main.main_fragment.MainViewPagerFragmentDirections
import com.vikmanz.shpppro.utils.extensions.isTrue
import com.vikmanz.shpppro.utils.extensions.log
import com.vikmanz.shpppro.utils.extensions.swapBoolean
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

// FakeData (true) or PhoneData (false) view first on myContacts
private const val FAKE_LIST_FIRST = true

/**
 * ViewModel for My Contacts Activity.
 */
@HiltViewModel
class MyContactsListViewModel @Inject constructor(
    contactsRepository: ContactsRepository<Contact>
) : BaseViewModel() {

    private val _repository = contactsRepository

    private var lastDeletedContact: Contact? = null
    private var lastDeletedContactPosition: Int = 0

    val isMultiselectMode = MutableLiveData(false)

    /**
     * Create fake contact list and Flow to take it from outside.
     */
    val contactList = _repository.contactList.map {
        it.map { contact ->
            ContactListItemState(
                contact = contact,
                onCheck = {
                    log("Lambda! mode before = ${isMultiselectMode.value}")
                    _repository.toggleContactSelectionState(contact)
                    isMultiselectMode.value = _repository.checkMultiselectState()
                    log("Lambda! mode after = ${isMultiselectMode.value}")
                }
            )
        }
    }

    /**
     * Variables to control swap between fake contacts and phone contacts lists.
     */
    val fakeListActivated = MutableLiveData(FAKE_LIST_FIRST)

    /**
     * Delete contact from list of contacts.
     */
    fun deleteContact(contact: Contact): Boolean {
        if (contact == lastDeletedContact) return false
        lastDeletedContact = contact
        lastDeletedContactPosition = getContactPosition(contact)
        _repository.deleteContact(contact)
        return true
    }

    /**
     * Delete contact from list of contacts.
     */
    fun restoreLastDeletedContact() {
        lastDeletedContact?.let {
            if (!_repository.isContainsContact(it)) {
                addContactToPosition(it, lastDeletedContactPosition)
                lastDeletedContact = null
            }
        }
    }

    /**
     * Get contact from list via index.
     */
    fun getContact(index: Int): Contact? {
        return _repository.getContact(index)
    }

    /**
     * Change contact list to fake contacts list.
     */
    fun changeContactList() {
        if (fakeListActivated.isTrue()) {
            _repository.setPhoneContacts()
        } else {
            viewModelScope.launch {
                _repository.setFakeContacts()
            }
        }
        fakeListActivated.swapBoolean()
    }

    fun onContactPressed(contactId: Long) {
        navigate(MainViewPagerFragmentDirections.startContactDetails(contactId))
    }

    /**
     * Get contact position in list of contacts.
     */
    private fun getContactPosition(contact: Contact): Int {
        return _repository.getContactPosition(contact)
    }

    /**
     * Add new contact to list of contacts to concrete index.
     */
    private fun addContactToPosition(contact: Contact, index: Int) {
        _repository.addContact(contact, index)
    }

    fun deleteMultipleContacts() {
        _repository.deleteMultipleContacts()
        isMultiselectMode.swapBoolean()
    }

}