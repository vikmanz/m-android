package com.vikmanz.shpppro.presentation.screens.main.main_fragment.my_contacts_list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.vikmanz.shpppro.common.extensions.isTrue
import com.vikmanz.shpppro.common.extensions.log
import com.vikmanz.shpppro.common.extensions.swapBoolean
import com.vikmanz.shpppro.common.model.ContactItem
import com.vikmanz.shpppro.common.model.User
import com.vikmanz.shpppro.domain.repository.ShPPContactsRepository
import com.vikmanz.shpppro.domain.usecases.contacts.GetAllUsersUseCase
import com.vikmanz.shpppro.presentation.base.BaseViewModel
import com.vikmanz.shpppro.presentation.screens.auth.sign_up_extended.SignUpExtendedFragmentDirections
import com.vikmanz.shpppro.presentation.screens.main.main_fragment.MainViewPagerFragmentDirections
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import ua.digitalminds.fortrainerapp.data.result.ApiResult
import javax.inject.Inject

// FakeData (true) or PhoneData (false) view first on myContacts
private const val FAKE_LIST_FIRST = true

/**
 * ViewModel for My Contacts Activity.
 */
@HiltViewModel
class MyContactsListViewModel @Inject constructor(
   private val getAllUsersUseCase: GetAllUsersUseCase
) : BaseViewModel() {

    private var lastDeletedContact: ContactItem? = null
    private var lastDeletedContactPosition: Int = 0

    val isMultiselectMode = MutableLiveData(false)

    private val _contactList = MutableStateFlow(emptyList<ContactItem>())

    init {
        viewModelScope.launch(Dispatchers.IO) {
            log("Start coroutine")
            getAllUsersUseCase().collect {

                when (it) {

                    is ApiResult.Loading -> {
                        log("loading")
                        emptyList<ContactItem>()
                    }

                    is ApiResult.Success -> {
                        log("api success")
                        log(it.value.toString())
                        _contactList.value = it.value.map { user -> ContactItem(contact = user) }
                    }

                    is ApiResult.NetworkError -> {
                        log("api network error!")
                        emptyList<ContactItem>()
                    }

                    is ApiResult.ServerError -> {
                        log("api server error!")
                        emptyList<ContactItem>()
                    }
                }
            }
            log("End coroutine")
        }
    }

    /**
     * Create fake contact list and Flow to take it from outside.
     */
    val contactList = _contactList.map {
        it.map { contactItem ->
            ContactItem(
                contact = contactItem.contact,
                isChecked = contactItem.isChecked,
                onCheck = {
                    log("Lambda! mode before = ${isMultiselectMode.value}")
                   // contactsRepository.toggleContactSelectionState(contactItem)
                   // isMultiselectMode.value = contactsRepository.checkMultiselectState()
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
       // contactsRepository.deleteContact(contact)
        return true
    }

    /**
     * Delete contact from list of contacts.
     */
    fun restoreLastDeletedContact() {
        lastDeletedContact?.let {
         //   if (!contactsRepository.isContainsContact(it)) {
         //       addContactToPosition(it, lastDeletedContactPosition)
         //       lastDeletedContact = null
         //   }
        }
    }

    /**
     * Get contact from list via index.
     */
//    fun getContact(index: Int): ContactItem? {
//        return contactList.value[index]
//    }

    /**
     * Change contact list to fake contacts list.
     */
    fun changeContactList() {
//        if (fakeListActivated.isTrue()) {
//            contactsRepository.setPhoneContacts()
//        } else {
//            viewModelScope.launch {
//                contactsRepository.setFakeContacts()
//            }
//        }
//        fakeListActivated.swapBoolean()
    }

    fun onContactPressed(contactId: Int) {
        navigate(MainViewPagerFragmentDirections.startContactDetails(contactId))
    }

    /**
     * Get contact position in list of contacts.
     */
    private fun getContactPosition(contact: ContactItem): Int {
//        return contactsRepository.getContactPosition(contact)
        return 0
    }

    /**
     * Add new contact to list of contacts to concrete index.
     */
    private fun addContactToPosition(contact: ContactItem, index: Int) {
//        contactsRepository.addContact(contact, index)
    }

    fun deleteMultipleContacts() {
//        contactsRepository.deleteMultipleContacts()
//        isMultiselectMode.swapBoolean()
    }

}