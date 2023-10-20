package com.vikmanz.shpppro.presentation.screens.main.main_fragment.my_contacts_list

import androidx.lifecycle.viewModelScope
import com.vikmanz.shpppro.constants.Constants
import com.vikmanz.shpppro.utils.extensions.log
import com.vikmanz.shpppro.data.holders.user_contact_list.ContactsListHolderHolder
import com.vikmanz.shpppro.data.model.contact_item.ContactItem
import com.vikmanz.shpppro.domain.usecases.contacts.AddContactUseCase
import com.vikmanz.shpppro.domain.usecases.contacts.DeleteContactUseCase
import com.vikmanz.shpppro.domain.usecases.contacts.GetUserContactsUseCase
import com.vikmanz.shpppro.presentation.base.BaseViewModel
import com.vikmanz.shpppro.presentation.screens.main.main_fragment.MainViewPagerFragmentDirections
import com.vikmanz.shpppro.presentation.utils.extensions.alsoLog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ua.digitalminds.fortrainerapp.data.result.ApiResult
import javax.inject.Inject

/**
 * ViewModel for My Contacts Activity.
 */
@HiltViewModel
class MyContactsListViewModel @Inject constructor(
    private val getUserContactsUseCase: GetUserContactsUseCase,
    private val deleteContactUseCase: DeleteContactUseCase,
    private val addContactUseCase: AddContactUseCase,
    private val contactListHolder: ContactsListHolderHolder,
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(MyContactsListState())
    val uiState: StateFlow<MyContactsListState> = _uiState.asStateFlow()

    private var lastDeletedContactItem: ContactItem? = null

    val contactList: Flow<List<ContactItem>> =
        contactListHolder.contactList.map { contactItemList ->
            contactItemList.map { contactItem ->
                ContactItem(
                    contact = contactItem.contact,
                    isChecked = contactItem.isChecked,
                    onDelete = ::deleteContact,
                    onClick = ::onContactClick,
                    onLongClick = ::onContactLongClick
                )
            }
        }

    init {
        getMyContacts()
    }

    private fun getMyContacts() {
        viewModelScope.launch(Dispatchers.IO) {
            log("Start coroutine getAllContacts")
            getUserContactsUseCase().collect {
                when (it) {
                    is ApiResult.Loading -> setProgressBar(true) alsoLog "loading"
                    is ApiResult.NetworkError -> setProgressBar(false) alsoLog "network error!"
                    is ApiResult.ServerError -> setProgressBar(false) alsoLog "server error!"
                    is ApiResult.Success -> setProgressBar(false) alsoLog "success!"
                }
            } alsoLog "End coroutine getAllContacts"
        }
    }

    private fun onContactClick(contactItem: ContactItem) {
        if (uiState.value.isMultiselectMode) {
            handleMultiselect(contactItem)
        } else {
            val contact = contactItem.contact
            navigate(MainViewPagerFragmentDirections.startContactDetails(contact))
        }
    }

    private fun onContactLongClick(contactItem: ContactItem) {
        if (!uiState.value.isMultiselectMode) {
            handleMultiselect(contactItem)
        }
    }

    private fun handleMultiselect(contactItem: ContactItem) {
        val isMultiselect = contactListHolder.changeContactItemCheckedState(contactItem.contact.id)
        _uiState.update { currentState ->
            currentState.copy(
                isMultiselectMode = isMultiselect
            )
        }
    }

    /**
     * Delete contact from list of contacts.
     */
    fun deleteContact(contactItem: ContactItem) {
        if (contactItem == lastDeletedContactItem) return
        lastDeletedContactItem = contactItem

        viewModelScope.launch(Dispatchers.IO) {
            log("Start coroutine deleteContact")
            deleteContactUseCase(contactItem.contact.id).collect { it ->
                when (it) {
                    is ApiResult.NetworkError -> {
                        contactListHolder.setLoadingStatus(
                            contactItem.contact.id,
                            false
                        ) alsoLog "network error !"
                    }

                    is ApiResult.ServerError -> {
                        contactListHolder.setLoadingStatus(
                            contactItem.contact.id,
                            false
                        ) alsoLog "server error!"
                    }

                    is ApiResult.Loading -> {
                        contactListHolder.setLoadingStatus(contactItem.contact.id, true)
                        log("loading")
                    }

                    is ApiResult.Success -> {
                        log("api success")
                        showSnackBar(true)
                        delay(Constants.SNACK_BAR_VIEW_TIME)
                        showSnackBar(false)
                    }
                }
            }
        } alsoLog "End coroutine deleteContact"
    }

    private fun showSnackBar(isShown: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(
                isShowSnackBar = isShown
            )
        }
    }


    fun restoreDeletedContact() {
        showSnackBar(false)
        lastDeletedContactItem?.let { restored ->
            viewModelScope.launch(Dispatchers.IO) {
                log("Start coroutine restoreDeletedContact")
                addContactUseCase(restored.contact.id).collect {
                    when (it) {
                        is ApiResult.NetworkError -> "" alsoLog "network error!"
                        is ApiResult.ServerError -> "" alsoLog "server error!"
                        is ApiResult.Loading -> "" alsoLog "loading"
                        is ApiResult.Success<*> -> {
                            log("api success")
                            lastDeletedContactItem = null
                        }
                    }
                }
            }
        } alsoLog "End coroutine restoreDeletedContact"
    }

    fun setSearchMode(isSearchMode: Boolean) {
        _uiState.update { it.copy(
            isSearchMode = isSearchMode
        ) }
    }

    private fun setProgressBar(isVisible: Boolean) {
        _uiState.update { it.copy(
            isLoadingData = isVisible
        ) }
    }

    fun startAddContact() {
        navigate(MainViewPagerFragmentDirections.startAddContact())
    }

}
