package com.vikmanz.shpppro.ui.main.main_fragment.my_contacts_list

import androidx.lifecycle.MutableLiveData
import com.vikmanz.shpppro.ui.base.BaseViewModel
import com.vikmanz.shpppro.data.model.ContactListItem
import com.vikmanz.shpppro.data.repository.interfaces.ContactsRepository
import com.vikmanz.shpppro.ui.main.main_fragment.MainViewPagerFragmentDirections
import com.vikmanz.shpppro.utils.extensions.isFalse
import com.vikmanz.shpppro.utils.extensions.isTrue
import com.vikmanz.shpppro.utils.extensions.swapBoolean
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

// FakeData (true) or PhoneData (false) view first on myContacts
private const val FAKE_LIST_FIRST = true

/**
 * ViewModel for My Contacts Activity.
 */
@HiltViewModel
class MyContactsListViewModel @Inject constructor(
    contactsRepository: ContactsRepository<ContactListItem>
) : BaseViewModel() {

    private val _repository = contactsRepository

    private var lastDeletedContact: ContactListItem? = null
    private var lastDeletedPosition: Int = 0

    val isMultiselectMode = MutableLiveData(false)


    init {
        _repository.setFakeContacts()
    }

    /**
     * Create fake contact list and Flow to take it from outside.
     */
    val contactList = _repository.contactList
//        .stateIn(
//        scope = viewModelScope,
//        initialValue = MutableStateFlow(listOf<ContactListItem>()),
//        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5_000))

    /**
     * Variables to control swap between fake contacts and phone contacts lists.
     */
    val fakeListActivated = MutableLiveData(FAKE_LIST_FIRST)

    /**
     * Delete contact from list of contacts.
     */
    fun deleteContact(item: ContactListItem): Boolean {
        if (item == lastDeletedContact) return false
        lastDeletedContact = item
        lastDeletedPosition = getContactPosition(item)
        _repository.deleteContactItem(item)
        return true
    }

    /**
     * Delete contact from list of contacts.
     */
    fun restoreLastDeletedContact() {
        lastDeletedContact?.let {
            if (!_repository.isContainsContactItem(it)) {
                addContactToPosition(it, lastDeletedPosition)
                lastDeletedContact = null
            }
        }
    }

    /**
     * Get contact from list via index.
     */
    fun getContact(index: Int): ContactListItem? {
        return _repository.getContactItem(index)
    }

    /**
     * Change contact list to fake contacts list.
     */
    fun changeContactList() {
        if (fakeListActivated.isTrue()) {
            _repository.setPhoneContacts()
        } else {
            _repository.setFakeContacts()
        }
        fakeListActivated.swapBoolean()
    }

    fun onContactPressed(item: ContactListItem) {
        if (isMultiselectMode.isFalse()) {
            navigate(MainViewPagerFragmentDirections.startContactDetails(item.contact.contactId))
        } else {
            if (_repository.checkContactInMultiselect(item)) {
                isMultiselectMode.swapBoolean()
            }
        }
    }

    /**
     * Get contact position in list of contacts.
     */
    private fun getContactPosition(item: ContactListItem): Int {
        return _repository.getContactItemPosition(item)
    }

    /**
     * Add new contact to list of contacts to concrete index.
     */
    private fun addContactToPosition(item: ContactListItem, index: Int) {
        _repository.addContactItem(item, index)
    }

    fun deleteMultipleContacts() {
        _repository.deleteMultipleContactItems()
        isMultiselectMode.swapBoolean()
    }


    fun swapSelectMode(item: ContactListItem) {
        isMultiselectMode.swapBoolean()
        if (isMultiselectMode.isTrue()) {
            onContactPressed(item)
        } else {
            _repository.clearMultiselect()
        }
    }
}