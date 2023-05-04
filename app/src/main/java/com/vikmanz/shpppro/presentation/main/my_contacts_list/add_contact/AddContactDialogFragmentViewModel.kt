package com.vikmanz.shpppro.presentation.main.my_contacts_list.add_contact

import android.net.Uri
import androidx.lifecycle.*
import com.vikmanz.shpppro.App

/**
 * ViewModel for My Contacts Activity.
 */
class AddContactDialogFragmentViewModel(
) : ViewModel() {

    val contactsReposetory = App.contactsRepository

    /**
     * Uri for image, which will be take from gallery.
     */
    var imgUri: Uri = Uri.EMPTY

    fun getFakePhotoUrl() = contactsReposetory.getCurrentContactPhotoUrl()

    fun createNewContact(
        name: String,
        career: String,
        email: String,
        phone: String,
        address: String,
        birthday: String
    ) {
        val newContact = contactsReposetory.createContact(
            photoUrl = contactsReposetory.getCurrentContactPhotoUrl(),
            photoUri = imgUri,
            photoIndex = contactsReposetory.getCurrentPhotoCounter(),
            name = name,
            career = career,
            email = email,
            phone = phone,
            address = address,
            birthday = birthday
        )
        contactsReposetory.addContact(newContact)
    }
}