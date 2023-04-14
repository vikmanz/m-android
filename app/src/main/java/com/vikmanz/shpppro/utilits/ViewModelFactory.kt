package com.vikmanz.shpppro.utilits

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vikmanz.shpppro.data.ContactsReposetory
import com.vikmanz.shpppro.ui.contacts.AddContactDialogFragmentViewModel
import com.vikmanz.shpppro.ui.contacts.ContactsViewModel

class ViewModelFactory(
    private val contactsReposetory: ContactsReposetory
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when (modelClass) {
            ContactsViewModel::class.java -> ContactsViewModel(contactsReposetory) as T
            AddContactDialogFragmentViewModel::class.java -> AddContactDialogFragmentViewModel(contactsReposetory) as T
            else -> {
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
}