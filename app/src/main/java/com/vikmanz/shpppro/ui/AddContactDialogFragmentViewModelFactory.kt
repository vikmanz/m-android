package com.vikmanz.shpppro.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vikmanz.shpppro.data.ContactsService

class AddContactDialogFragmentViewModelFactory(
    private val contactsService: ContactsService
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return  AddContactDialogFragmentViewModel(contactsService) as T
    }

}