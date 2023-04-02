package com.vikmanz.shpppro.ui

import android.net.Uri
import androidx.lifecycle.*
import com.vikmanz.shpppro.data.ContactsService

/**
 * ViewModel for My Contacts Activity.
 */
class AddContactDialogFragmentViewModel(
    val contactsService: ContactsService
) : ViewModel() {

    /**
     * Uri for image, which will be take from gallery.
     */
    var imgUri: Uri = Uri.EMPTY

    fun getFakePhotoUrl() = contactsService.getCurrentContactPhotoUrl()

    fun createNewContact(
        name: String,
        career: String,
        email: String,
        phone: String,
        address: String,
        birthday: String
    ) {
        val newContact = contactsService.createContact(
            photoUrl = contactsService.getCurrentContactPhotoUrl(),
            photoUri = imgUri,
            photoIndex = contactsService.getCurrentPhotoCounter(),
            name = name,
            career = career,
            email = email,
            phone = phone,
            address = address,
            birthday = birthday
        )
        contactsService.addContact(newContact)
    }
}