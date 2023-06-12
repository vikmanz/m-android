package com.vikmanz.shpppro.ui.main.contact_details

import androidx.lifecycle.SavedStateHandle
import com.vikmanz.shpppro.ui.base.BaseViewModel
import com.vikmanz.shpppro.data.model.Contact
import com.vikmanz.shpppro.data.model.ContactListItem
import com.vikmanz.shpppro.data.repository.interfaces.ContactsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ContactDetailsViewModel @Inject constructor(
    contactsRepository: ContactsRepository<ContactListItem>,
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private val navArgs = ContactDetailsFragmentArgs.fromSavedStateHandle(savedStateHandle)

    private val contact =
        requireNotNull(contactsRepository.findContactItem(navArgs.contactID)).contact

    val contactName = contact.contactName
    val contactCareer = contact.contactCareer
    val contactAddress = contact.contactAddress
    val contactPhoto = contact.contactPhotoLink

    fun onButtonBackPressed() {
        navigateBack()
    }

}