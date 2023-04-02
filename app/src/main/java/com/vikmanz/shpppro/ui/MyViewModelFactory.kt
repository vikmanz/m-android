package com.vikmanz.shpppro.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vikmanz.shpppro.data.ContactsService

class MyViewModelFactory(
    private val contactsService: ContactsService
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when (modelClass) {
            MyContactsViewModel::class.java -> MyContactsViewModel(contactsService) as T
            AddContactDialogFragmentViewModel::class.java -> AddContactDialogFragmentViewModel(contactsService) as T
            else -> {
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
}