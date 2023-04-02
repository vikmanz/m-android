package com.vikmanz.shpppro.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vikmanz.shpppro.data.ContactsReposetory

class MyViewModelFactory(
    private val contactsReposetory: ContactsReposetory
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when (modelClass) {
            MyContactsViewModel::class.java -> MyContactsViewModel(contactsReposetory) as T
            AddContactDialogFragmentViewModel::class.java -> AddContactDialogFragmentViewModel(contactsReposetory) as T
            else -> {
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
}