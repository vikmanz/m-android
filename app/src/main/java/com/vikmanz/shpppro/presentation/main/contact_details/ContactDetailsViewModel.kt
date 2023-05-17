package com.vikmanz.shpppro.presentation.main.contact_details

import com.vikmanz.shpppro.App
import com.vikmanz.shpppro.data.contact_model.Contact
import com.vikmanz.shpppro.presentation.navigator.Navigator
import com.vikmanz.shpppro.presentation.base.BaseViewModel

class ContactDetailsViewModel(
    private val navigator: Navigator,
    customArgument: ContactDetailsFragment.CustomArgument
) : BaseViewModel() {

    private val contactsRepository = App.contactsRepository

    private val id = customArgument.contactID

    //TODO is it possible to not find id?
    private val contact: Contact = contactsRepository.findContact(id) ?: contactsRepository.generateRandomContact()

    //TODO I think, it`s okay to use directly property contact in fragment, this approach is also good
    val name = contact.contactName
    val career = contact.contactCareer
    val homeAddress = contact.contactAddress

    val photoUrl = contact.contactPhotoUrl
    val photoUri = contact.contactPhotoUri

    fun onButtonBackPressed() {
        navigator.goBack()
    }
}