package com.vikmanz.shpppro.presentation.main.my_profile

import com.vikmanz.shpppro.presentation.base.BaseViewModel
import com.vikmanz.shpppro.navigator.Navigator
import com.vikmanz.shpppro.presentation.main.my_contacts_list.MyContactsListFragment

class MyProfileViewModel(
    private val navigator: Navigator,
    customArgument: MyProfileFragment.CustomArgument
) : BaseViewModel() {

    val userEmail = customArgument.email

    fun onMyContactsPressed() {
      //  navigator.launchMyContacts(MyContactsListFragment.CustomArgument(name = "to_MyContacts_args"))
       navigator.launchMyContacts(MyContactsListFragment.CustomArgument())
    }

}