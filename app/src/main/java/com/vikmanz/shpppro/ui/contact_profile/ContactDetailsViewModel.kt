package com.vikmanz.shpppro.ui.contact_profile

import com.vikmanz.shpppro.App
import com.vikmanz.shpppro.data.contactModel.Contact
import com.vikmanz.shpppro.navigator.Navigator
import com.vikmanz.shpppro.ui.base.BaseViewModel

class ContactDetailsViewModel(
    private val navigator: Navigator,
    customArgument: ContactDetailsFragment.CustomArgument
) : BaseViewModel() {

    private val contactsService = App.contactsReposetory

    private val id = customArgument.contactID

    private val contact: Contact = contactsService.findContact(id) ?: contactsService.generateRandomContact()

    val name = contact.contactName
    val career = contact.contactCareer
    val homeAddress = contact.contactAddress

    val photoUrl = contact.contactPhotoUrl
    val photoUri = contact.contactPhotoUri

    fun onBackPressed() {
        navigator.goBack()
    }
}