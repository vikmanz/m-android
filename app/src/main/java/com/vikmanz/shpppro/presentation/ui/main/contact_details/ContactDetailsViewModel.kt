package com.vikmanz.shpppro.presentation.ui.main.contact_details

import androidx.lifecycle.SavedStateHandle
import com.vikmanz.shpppro.data.model.Contact
import com.vikmanz.shpppro.domain.repository.ContactsRepositoryLocal
import com.vikmanz.shpppro.presentation.ui.base.BaseViewModel
import com.vikmanz.shpppro.ui.main.contact_details.ContactDetailsFragmentArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ContactDetailsViewModel @Inject constructor(
    contactsRepository: ContactsRepositoryLocal<Contact>,
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private val navArgs = ContactDetailsFragmentArgs.fromSavedStateHandle(savedStateHandle)

    private val contact =
        requireNotNull(contactsRepository.findContact(navArgs.contactID))

    val contactName = contact.contactName
    val contactCareer = contact.contactCareer
    val contactAddress = contact.contactAddress
    val contactPhoto = contact.contactPhotoLink

    fun onButtonBackPressed() {
        navigateBack()
    }

}