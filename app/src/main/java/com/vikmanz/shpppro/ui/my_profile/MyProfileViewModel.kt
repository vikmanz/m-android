package com.vikmanz.shpppro.ui.my_profile

import com.vikmanz.shpppro.ui.base.BaseViewModel
import com.vikmanz.shpppro.navigator.Navigator
import com.vikmanz.shpppro.ui.my_contacts.MyContactsFragment

class MyProfileViewModel(
    private val navigator: Navigator,
    customArgument: MyProfileFragment.CustomArgument
) : BaseViewModel() {

    val userEmail = customArgument.email

    fun onMyContactsPressed() {
        navigator.launchMyContacts(MyContactsFragment.CustomArgument(name = "to_MyContacts_args"))
    }

}