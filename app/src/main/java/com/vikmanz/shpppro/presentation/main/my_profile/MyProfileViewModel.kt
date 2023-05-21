package com.vikmanz.shpppro.presentation.main.my_profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vikmanz.shpppro.data.datastore.interfaces.MyPreferences
import com.vikmanz.shpppro.presentation.navigator.Navigator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MyProfileViewModel @Inject constructor(
    navigator: Navigator,
    dataStore: MyPreferences,
    email: String = "TEST.test@gmail.com"
) : ViewModel() {

    val userEmail = email

    private val _navigator = navigator
    private val _dataStore = dataStore

    fun onMyContactsPressed() {
        _navigator.launchMyContacts()
    }

    fun clearSavedUserData() {
        viewModelScope.launch(Dispatchers.IO) {
            _dataStore.clearUser()
        }
    }

}