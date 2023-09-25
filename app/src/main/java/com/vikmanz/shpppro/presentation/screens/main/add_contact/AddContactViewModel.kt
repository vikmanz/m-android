package com.vikmanz.shpppro.presentation.screens.main.add_contact

import androidx.lifecycle.viewModelScope
import com.vikmanz.shpppro.common.extensions.log
import com.vikmanz.shpppro.data.model.ContactItem
import com.vikmanz.shpppro.data.model.User
import com.vikmanz.shpppro.domain.usecases.contacts.AddContactUseCase
import com.vikmanz.shpppro.domain.usecases.contacts.GetAllUsersUseCase
import com.vikmanz.shpppro.presentation.base.BaseViewModel
import com.vikmanz.shpppro.presentation.screens.main.main_fragment.MainViewPagerFragmentDirections
import com.vikmanz.shpppro.presentation.utils.extensions.alsoLog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ua.digitalminds.fortrainerapp.data.result.ApiResult
import javax.inject.Inject

/**
 * ViewModel for My Contacts Activity.
 */
@HiltViewModel
class AddContactViewModel @Inject constructor(
    private val getAllUsersUseCase: GetAllUsersUseCase,
    private val addContactUseCase: AddContactUseCase,
) : BaseViewModel() {

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

    private fun onContactClick(contactItem: ContactItem) {
        val contact = contactItem.contact
        navigate(MainViewPagerFragmentDirections.startContactDetails(contact))
    }

    private fun updateContactList(newContactList: List<User>) {
        _contactList.value = newContactList
            .map { user ->
                ContactItem(
                    contact = user,
                    onClick = ::onContactClick,
                    onPlusClick = ::addContact
                )
            }
    }

    private fun addContact(addedContact: ContactItem) {
        viewModelScope.launch(Dispatchers.IO) {
            log("Start coroutine restoreDeletedContact")
            addContactUseCase(addedContact.contact.id).collect {
                when (it) {
                    is ApiResult.NetworkError -> "" alsoLog "network error!"
                    is ApiResult.ServerError -> "" alsoLog "server error!"
                    is ApiResult.Loading -> "" alsoLog "loading"
                    is ApiResult.Success -> "" alsoLog "success"
                }
                log("End coroutine restoreDeletedContact")
            }
        }
    }

    fun onButtonBackPressed() {
        navigateBack()
    }
}

