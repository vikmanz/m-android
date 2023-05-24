package com.vikmanz.shpppro.ui.main.my_profile

import androidx.lifecycle.SavedStateHandle
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
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private val navArgs = MyProfileFragmentArgs.fromSavedStateHandle(savedStateHandle)
    private val _dataStore = dataStore

    val userEmail = navArgs.email

    fun onMyContactsPressed() {
        navigate(MyProfileFragmentDirections.startMyContacts())
    }

    fun clearSavedUserData() {
        viewModelScope.launch(Dispatchers.IO) {
            _dataStore.clearUser()
        }
    }

}