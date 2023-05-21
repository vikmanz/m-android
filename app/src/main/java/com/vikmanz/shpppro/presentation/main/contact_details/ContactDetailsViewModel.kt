package com.vikmanz.shpppro.presentation.main.contact_details

import androidx.lifecycle.ViewModel
import com.vikmanz.shpppro.data.contact_model.Contact
import com.vikmanz.shpppro.data.repository.interfaces.Repository
import com.vikmanz.shpppro.presentation.navigator.Navigator
import javax.inject.Inject

class ContactDetailsViewModel @Inject constructor(
    navigator: Navigator,
    contactsRepository: Repository<Contact>,
    private val contactID: Long
) : ViewModel() {


    private val id = contactID

    private val _navigator = navigator


    private val contact: Contact = contactsRepository.findContact(id) ?: contactsRepository.generateRandomContact()

    val name = contact.contactName
    val career = contact.contactCareer
    val homeAddress = contact.contactAddress
    val photoLink = contact.contactPhotoLink

    fun onButtonBackPressed() {
        _navigator.goBack()
    }
}