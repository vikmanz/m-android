package com.vikmanz.shpppro.presentation.main.my_contacts_list.add_contact

import android.net.Uri
import androidx.lifecycle.*
import com.vikmanz.shpppro.App

/**
 * ViewModel for My Contacts Activity.
 */
class AddContactDialogFragmentViewModel(
) : ViewModel() {

    val contactsRepository = App.contactsRepository

    /**
     * Uri for image, which will be take from gallery.
     */
    var imgUri: Uri = Uri.EMPTY

    fun getFakePhotoUrl() = contactsRepository.getCurrentContactPhotoUrl()

    fun createNewContact(
        name: String,
        career: String,
        email: String,
        phone: String,
        address: String,
        birthday: String
    ) {
        val newContact = contactsRepository.createContact(
            photoUrl = contactsRepository.getCurrentContactPhotoUrl(),
            photoUri = imgUri,
            photoIndex = contactsRepository.getCurrentPhotoCounter(),
            name = name,
            career = career,
            email = email,
            phone = phone,
            address = address,
            birthday = birthday
        )
        contactsRepository.addContact(newContact)
    }
}