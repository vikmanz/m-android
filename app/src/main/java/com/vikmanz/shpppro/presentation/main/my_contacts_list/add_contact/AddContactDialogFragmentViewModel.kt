package com.vikmanz.shpppro.presentation.main.my_contacts_list.add_contact

import android.annotation.SuppressLint
import android.net.Uri
import androidx.lifecycle.*
import com.vikmanz.shpppro.App
import com.vikmanz.shpppro.constants.Constants
import org.jetbrains.annotations.NotNull

/**
 * ViewModel for My Contacts Activity.
 */
class AddContactDialogFragmentViewModel : ViewModel() {

    private val contactsRepository = App.contactsRepository

    // avatar
    val currentPhoto = MutableLiveData<Any>(getFakePhotoUrl())
    private fun getFakePhotoUrl() = contactsRepository.getCurrentContactPhotoUrl()

    fun changeFakePhotoToNext() {
        contactsRepository.incrementPhotoCounter()
        currentPhoto.value = getFakePhotoUrl()
    }

    fun setPhotoUri(uri: Uri) {
        currentPhoto.value = uri
    }

    fun createNewContact(
        name: String,
        career: String,
        email: String,
        phone: String,
        address: String,
        birthday: String
    ) {
        val newContact = contactsRepository.createContact(
            contactPhotoLink = getCurrentPhoto(),
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

    private fun getCurrentPhoto(): Any = with(currentPhoto.value) {
        if (this is Uri) this else getFakePhotoUrl()
    }


}