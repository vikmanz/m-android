package com.vikmanz.shpppro.presentation.main.my_profile

import androidx.lifecycle.viewModelScope
import com.vikmanz.shpppro.App
import com.vikmanz.shpppro.presentation.base.BaseViewModel
import com.vikmanz.shpppro.presentation.main.my_contacts_list.MyContactsListFragment
import com.vikmanz.shpppro.presentation.navigator.Navigator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyProfileViewModel (
    private val navigator: Navigator,
    customArgument: MyProfileFragment.CustomArgument
) : BaseViewModel() {

    val userEmail = customArgument.email

    fun onMyContactsPressed() {
       navigator.launchMyContacts(MyContactsListFragment.CustomArgument())
    }

    fun clearSavedUserData() {
        val dataStore = App.dataStore
        viewModelScope.launch(Dispatchers.IO) {
            dataStore.clearUser()
        }
    }

}