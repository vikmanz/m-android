package com.vikmanz.shpppro.presentation.screens.main.add_contact

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vikmanz.shpppro.domain.repository.ShPPContactsRepository

/**
 * ViewModel for My Contacts Activity.
 */
//@HiltViewModel
class AddContactDialogFragmentViewModel (//@Inject constructor(
    private val contactsRepository: ShPPContactsRepository
) : ViewModel() {

    // avatar
    val currentPhoto = MutableLiveData<Any>(getFakePhotoUrl())
    private fun getFakePhotoUrl() = "" // contactsRepository.getCurrentContactPhotoUrl()

    fun changeFakePhotoToNext() {
        //contactsRepository.incrementPhotoCounter()
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
//        val newContactItem = contactsRepository.createContact(      //todo change!
//            contactPhotoLink = getCurrentPhoto(),
//            photoIndex = contactsRepository.getCurrentPhotoCounter(),
//            name = name,
//            career = career,
//            email = email,
//            phone = phone,
//            address = address,
//            birthday = birthday
//        )
//        contactsRepository.addContact(newContactItem)
    }

    private fun getCurrentPhoto(): Any = with(currentPhoto.value) {
        if (this is Uri) this else getFakePhotoUrl()
    }


}