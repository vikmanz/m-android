package com.vikmanz.shpppro.presentation.screens.main.main_fragment.my_contacts_list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.vikmanz.shpppro.common.extensions.log
import com.vikmanz.shpppro.data.model.ContactItem
import com.vikmanz.shpppro.data.model.User
import com.vikmanz.shpppro.domain.usecases.contacts.DeleteContactUseCase
import com.vikmanz.shpppro.domain.usecases.contacts.GetAllUsersUseCase
import com.vikmanz.shpppro.presentation.base.BaseViewModel
import com.vikmanz.shpppro.presentation.screens.main.main_fragment.MainViewPagerFragmentDirections
import com.vikmanz.shpppro.presentation.utils.extensions.alsoLog
import com.vikmanz.shpppro.presentation.utils.extensions.alsoLogItAs
import com.vikmanz.shpppro.presentation.utils.extensions.copyItem
import com.vikmanz.shpppro.presentation.utils.extensions.findInList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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
    private val getAllUsersUseCase: GetAllUsersUseCase,
    private val deleteContactUseCase: DeleteContactUseCase,
) : BaseViewModel() {

    private var lastDeletedContactItem: ContactItem? = null

    val isMultiselectMode = MutableLiveData(false)

    private val _contactList = MutableStateFlow(emptyList<ContactItem>())
    val contactList: StateFlow<List<ContactItem>> = _contactList.asStateFlow()

    init {
        getAllContacts()
    }

    private fun getAllContacts() {
        viewModelScope.launch(Dispatchers.IO) {
            log("Start coroutine getAllContacts")
            getAllUsersUseCase().collect {
                when (it) {
                    is ApiResult.Loading -> "" alsoLog "loading"
                    is ApiResult.NetworkError -> "" alsoLog "network error!"
                    is ApiResult.ServerError -> "" alsoLog "server error!"
                    is ApiResult.Success -> updateContactList(it.value) alsoLog "api success! \n ${it.value}"
                }
            } alsoLog "End coroutine getAllContacts"
        }
    }

    private fun updateContactList(newContactList: List<User>) {
        _contactList.value = newContactList
            .map { user ->
                ContactItem(
                    contact = user,
                    onDelete = ::deleteContact,
                    onClick = ::onContactClick,
                    onLongClick = ::onContactLongClick,
                )
            }
    }

    private fun onContactClick(contactItem: ContactItem) {
        if (isMultiselectMode.value == true) {
            changeContactItemCheckedState(contactItem)
        } else {
            val contactId = contactItem.contact.id
            navigate(MainViewPagerFragmentDirections.startContactDetails(contactId))
        }
    }

    private fun changeContactItemCheckedState(contactItem: ContactItem) {
        _contactList.value = _contactList.value.toMutableList().apply {
            copyItem(contactItem) {
                copy(isChecked = !contactItem.isChecked)
            }
            if (none { it.isChecked }) isMultiselectMode.postValue(false)
        }
    }

    private fun onContactLongClick(contactItem: ContactItem) {
        if (isMultiselectMode.value == false) {
            isMultiselectMode.postValue(true)
            changeContactItemCheckedState(contactItem)
        }
    }

    /**
     * Delete contact from list of contacts.
     */
    private fun deleteContact(contactItem: ContactItem) {
        if (contactItem == lastDeletedContactItem) return
        lastDeletedContactItem = contactItem

        viewModelScope.launch(Dispatchers.IO) {
            log("Start coroutine deleteContact")
            deleteContactUseCase(contactItem.contact.id).collect { it ->
                when (it) {
                    //is ApiResult.NetworkError -> "" alsoLog "network error!"
                    //is ApiResult.ServerError -> "" alsoLog "server error!"

                    is ApiResult.Loading -> {
                        _contactList.value = _contactList.value.toMutableList().apply {
                            copyItem(contactItem) {
                                copy(isLoading = true)
                            }
                        }
                        log("loading")
                    }
//
//                    is ApiResult.Success -> {
//                        log("api success")
//                        _contactList.value = _contactList.value.toMutableList().apply {
//                            remove(contactItem)
//                        }
//                        //log(it.value.toString())
//                    }

                    else -> {
                        log("api success")
                        _contactList.value = _contactList.value.toMutableList().apply {
                            remove(findInList(contactItem))
                        }
                    }

                }
            }
            log("End coroutine deleteContact")
        }
    }

    /**
     * Variables to control swap between fake contacts and phone contacts lists.
     */
    val fakeListActivated = MutableLiveData(FAKE_LIST_FIRST)


    /**
     * Delete contact from list of contacts.
     */
    fun restoreLastDeletedContact() {
//        lastDeletedContact?.let {
//            //   if (!contactsRepository.isContainsContact(it)) {
//            //       addContactToPosition(it, lastDeletedContactPosition)
//            //       lastDeletedContact = null
//            //   }
//        }
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