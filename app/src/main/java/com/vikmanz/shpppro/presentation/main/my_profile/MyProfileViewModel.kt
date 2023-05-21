package com.vikmanz.shpppro.presentation.main.my_profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vikmanz.shpppro.data.datastore.interfaces.MyPreferences
import com.vikmanz.shpppro.presentation.navigator.Navigator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MyProfileViewModel @Inject constructor(
    private val navigator: Navigator,
    private val dataStore: MyPreferences,
    private val email: String
) : ViewModel() {

    val userEmail = email

    fun onMyContactsPressed() {
       navigator.launchMyContacts()
    }

    fun clearSavedUserData() {
        viewModelScope.launch(Dispatchers.IO) {
            dataStore.clearUser()
        }
    }

}