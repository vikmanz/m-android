package com.vikmanz.shpppro.ui.my_profile

import com.vikmanz.shpppro.ui.base.BaseViewModel
import com.vikmanz.shpppro.navigator.Navigator
import com.vikmanz.shpppro.ui.my_contacts.ContactsFragment

class MyProfileViewModel(
    private val navigator: Navigator,
    customArgs: MyProfileFragment.CustomArgs
) : BaseViewModel() {

    val userEmail = customArgs.email

    fun onMyContactsPressed() {
        navigator.launchMyContacts(ContactsFragment.CustomArgs())
    }

}