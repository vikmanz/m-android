package com.vikmanz.shpppro.presentation.screens.main.contact_details

import androidx.lifecycle.SavedStateHandle
import com.vikmanz.shpppro.domain.repository.ContactsRepositoryLocal
import com.vikmanz.shpppro.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ContactDetailsViewModel @Inject constructor(
    contactsRepository: ContactsRepositoryLocal,
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private val navArgs = ContactDetailsFragmentArgs.fromSavedStateHandle(savedStateHandle)

    private val contactItem =
        requireNotNull(contactsRepository.findContact(navArgs.contactID))

    val contactName = contactItem.contact.contactName
    val contactCareer = contactItem.contact.contactCareer
    val contactAddress = contactItem.contact.contactAddress
    val contactPhoto = contactItem.contact.contactPhotoLink

    fun onButtonBackPressed() {
        navigateBack()
    }

}