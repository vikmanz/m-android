package com.vikmanz.shpppro.presentation.screens.main.main_fragment.my_contacts_list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.vikmanz.shpppro.common.extensions.isTrue
import com.vikmanz.shpppro.common.extensions.log
import com.vikmanz.shpppro.common.extensions.swapBoolean
import com.vikmanz.shpppro.common.model.ContactItem
import com.vikmanz.shpppro.domain.repository.ContactsRepositoryLocal
import com.vikmanz.shpppro.domain.usecases.contacts.GetAllUsersUseCase
import com.vikmanz.shpppro.presentation.base.BaseViewModel
import com.vikmanz.shpppro.presentation.screens.main.main_fragment.MainViewPagerFragmentDirections
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
    private val contactsRepository: ContactsRepositoryLocal,
    private val getAllUsersUseCase: GetAllUsersUseCase
) : BaseViewModel() {

    private var lastDeletedContact: ContactItem? = null
    private var lastDeletedContactPosition: Int = 0

    val isMultiselectMode = MutableLiveData(false)

    /**
     * Create fake contact list and Flow to take it from outside.
     */
    val contactList = contactsRepository.contactList.map {
        it.map { contactItem ->
            ContactItem(
                contact = contactItem.contact,
                isChecked = contactItem.isChecked,
                onCheck = {
                    log("Lambda! mode before = ${isMultiselectMode.value}")
                    contactsRepository.toggleContactSelectionState(contactItem)
                    isMultiselectMode.value = contactsRepository.checkMultiselectState()
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
    fun deleteContact(contact: ContactItem): Boolean {
        if (contact == lastDeletedContact) return false
        lastDeletedContact = contact
        lastDeletedContactPosition = getContactPosition(contact)
        contactsRepository.deleteContact(contact)
        return true
    }

    /**
     * Delete contact from list of contacts.
     */
    fun restoreLastDeletedContact() {
        lastDeletedContact?.let {
            if (!contactsRepository.isContainsContact(it)) {
                addContactToPosition(it, lastDeletedContactPosition)
                lastDeletedContact = null
            }
        }
    }

    /**
     * Get contact from list via index.
     */
    fun getContact(index: Int): ContactItem? {
        return contactsRepository.getContact(index)
    }

    /**
     * Change contact list to fake contacts list.
     */
    fun changeContactList() {
        if (fakeListActivated.isTrue()) {
            contactsRepository.setPhoneContacts()
        } else {
            viewModelScope.launch {
                contactsRepository.setFakeContacts()
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
    private fun getContactPosition(contact: ContactItem): Int {
        return contactsRepository.getContactPosition(contact)
    }

    /**
     * Add new contact to list of contacts to concrete index.
     */
    private fun addContactToPosition(contact: ContactItem, index: Int) {
        contactsRepository.addContact(contact, index)
    }

    fun deleteMultipleContacts() {
        contactsRepository.deleteMultipleContacts()
        isMultiselectMode.swapBoolean()
    }

}