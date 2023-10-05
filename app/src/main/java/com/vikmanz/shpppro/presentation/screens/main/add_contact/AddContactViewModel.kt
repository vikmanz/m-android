package com.vikmanz.shpppro.presentation.screens.main.add_contact

import androidx.lifecycle.viewModelScope
import com.vikmanz.shpppro.common.extensions.log
import com.vikmanz.shpppro.data.holders.user_contact_list.ContactsListHolder
import com.vikmanz.shpppro.data.model.AddContactItem
import com.vikmanz.shpppro.data.model.ContactItem
import com.vikmanz.shpppro.data.model.User
import com.vikmanz.shpppro.domain.usecases.contacts.AddContactUseCase
import com.vikmanz.shpppro.domain.usecases.contacts.GetAllUsersUseCase
import com.vikmanz.shpppro.presentation.base.BaseViewModel
import com.vikmanz.shpppro.presentation.utils.extensions.alsoLog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
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
    private val contactListHolder: ContactsListHolder,
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(AddContactState())
    val uiState: StateFlow<AddContactState> = _uiState.asStateFlow()

    private val _contactList = MutableStateFlow(emptyList<AddContactItem>())
    val contactList: StateFlow<List<AddContactItem>> = _contactList.asStateFlow()

    init {
        getAllContacts()
    }

    private fun getAllContacts() {
        viewModelScope.launch(Dispatchers.IO) {
            log("Start coroutine getAllContacts")
            getAllUsersUseCase().collect {
                when (it) {
                    is ApiResult.Loading -> setProgressBar(true) alsoLog "loading"
                    is ApiResult.NetworkError -> setProgressBar(false) alsoLog "network error!"
                    is ApiResult.ServerError -> setProgressBar(false) alsoLog "server error!"
                    is ApiResult.Success -> {
                        setProgressBar(false)
                        updateContactList(it.value) alsoLog "api success! \n ${it.value}"
                    }
                }
            } alsoLog "End coroutine getAllContacts"
        }
    }

    private fun onContactClick(contactItem: AddContactItem) {
        val contact = contactItem.contact
        navigate(AddContactFragmentDirections.startContactDetails(contact))
    }

    private fun updateContactList(newContactList: List<User>) {
        val haveContactsList: StateFlow<List<ContactItem>> = contactListHolder.contactList
        _contactList.value = newContactList
            .filter { user ->
                !haveContactsList.value.any { it.contact.id == user.id }
            }
            .map { user ->
                AddContactItem(
                    contact = user,
                    onClick = ::onContactClick,
                    onPlusClick = ::addContact
                )
            }
    }

    private fun addContact(addedContact: AddContactItem) {
        _uiState.update {
            it.copy(
                contactEmail = addedContact.contact.email ?: "",
                isShowAlertDialog = true,
                onAcceptAlertDialog = { addContactToServer(addedContact) },
                onDismissAlertDialog = { clearAlertDialog() }
            )
        }
    }

    private fun addContactToServer(addedContact: AddContactItem) {
        clearAlertDialog()
        val addedContactIndex = _contactList.value.indexOf(addedContact)
        viewModelScope.launch(Dispatchers.IO) {
            log("Start coroutine restoreDeletedContact")
            addContactUseCase(addedContact.contact.id).collect { it ->
                when (it) {
                    is ApiResult.NetworkError, is ApiResult.ServerError -> {
                        _contactList.update { currentList ->
                            currentList.toMutableList().apply {
                                set(
                                    addedContactIndex, get(addedContactIndex).copy(
                                        isLoading = false,
                                        isError = true
                                    )
                                )
                            }
                        } alsoLog "error!"
                    }

                    is ApiResult.Loading -> {
                        _contactList.update { currentList ->
                            currentList.toMutableList().apply {
                                set(
                                    addedContactIndex, get(addedContactIndex).copy(
                                        isLoading = true,
                                        isError = false
                                    )
                                )
                            }
                        } alsoLog "loading"
                    }

                    is ApiResult.Success<*> -> {
                        _contactList.update { currentList ->
                            currentList.toMutableList().apply {
                                set(
                                    addedContactIndex, get(addedContactIndex).copy(
                                        isLoading = false,
                                        isAdded = true
                                    )
                                )
                            }
                        } alsoLog "success"
                    }
                }
                log("End coroutine restoreDeletedContact")
            }
        }
    }

    fun onButtonBackPressed() {
        navigateBack()
    }

    fun setSearchMode(isSearchMode: Boolean) {
        _uiState.update {
            it.copy(
                isSearchMode = isSearchMode
            )
        }
    }

    private fun setProgressBar(isVisible: Boolean) {
        _uiState.update {
            it.copy(
                isLoadingUsers = isVisible
            )
        }
    }


    private fun clearAlertDialog() {
        _uiState.update {
            it.copy(
                contactEmail = "",
                isShowAlertDialog = false,
                onAcceptAlertDialog = {},
                onDismissAlertDialog = {}
            )
        }
    }
}

