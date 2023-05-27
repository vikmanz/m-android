package com.vikmanz.shpppro.ui.main.contact_details

import androidx.lifecycle.SavedStateHandle
import com.vikmanz.shpppro.base.BaseViewModel
import com.vikmanz.shpppro.data.contact_model.ContactListItem
import com.vikmanz.shpppro.data.repository.interfaces.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ContactDetailsViewModel @Inject constructor(
    contactsRepository: Repository<ContactListItem>,
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