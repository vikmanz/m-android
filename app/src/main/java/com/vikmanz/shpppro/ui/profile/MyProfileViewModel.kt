package com.example.fragmentsnavigatortest.screens.edit

import com.vikmanz.shpppro.ui.base.BaseViewModel
import com.vikmanz.shpppro.navigator.Navigator
import com.vikmanz.shpppro.ui.contacts.ContactsFragment
import com.vikmanz.shpppro.ui.profile.MyProfileFragment

class MyProfileViewModel(
    private val navigator: Navigator,
    customArgs: MyProfileFragment.CustomArgs
) : BaseViewModel() {

    val userEmail = customArgs.emailToParse

    fun onMyContactsPressed() {
        navigator.launchMyContacts(ContactsFragment.CustomArgs())
    }

}