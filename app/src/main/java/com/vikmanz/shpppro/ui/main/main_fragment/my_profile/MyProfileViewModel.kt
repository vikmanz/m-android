package com.vikmanz.shpppro.ui.main.main_fragment.my_profile

import androidx.lifecycle.viewModelScope
import com.vikmanz.shpppro.base.BaseViewModel
import com.vikmanz.shpppro.data.datastore.interfaces.MyPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyProfileViewModel @Inject constructor(
    dataStore: MyPreferences,
) : BaseViewModel() {

    //private val navArgs = MainViewPagerFragmentArgs.fromSavedStateHandle(savedStateHandle)
    private val _dataStore = dataStore

    var userEmail = "" //navArgs.email

    fun setEmail(email: String) {
        userEmail = email
    }

    fun onMyContactsPressed() {
       // navigate(MyProfileFragmentDirections.startMyContacts())
    }

    fun clearSavedUserData() {
        viewModelScope.launch(Dispatchers.IO) {
            _dataStore.clearUser()
        }
    }

}