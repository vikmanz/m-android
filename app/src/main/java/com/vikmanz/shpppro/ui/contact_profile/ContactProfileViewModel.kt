package com.example.fragmentsnavigatortest.screens.edit

import android.net.Uri
import com.vikmanz.shpppro.App
import com.vikmanz.shpppro.data.contactModel.Contact
import com.vikmanz.shpppro.navigator.Navigator
import com.vikmanz.shpppro.ui.base.BaseViewModel
import com.vikmanz.shpppro.ui.contact_profile.ContactProfileFragment
import com.vikmanz.shpppro.utilits.setContactPhoto
import com.vikmanz.shpppro.utilits.setContactPhotoFromUri

class ContactProfileViewModel(
    private val navigator: Navigator,
    customArgs: ContactProfileFragment.CustomArgs
) : BaseViewModel() {

    private val contactsService = App.contactsReposetory

    private val id = customArgs.contactID

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