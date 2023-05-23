package com.vikmanz.shpppro.ui.main.my_profile

import androidx.lifecycle.viewModelScope
import com.vikmanz.shpppro.base.BaseViewModelWithArgs
import com.vikmanz.shpppro.data.datastore.interfaces.MyPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyProfileViewModel @Inject constructor(
    dataStore: MyPreferences,
) : BaseViewModelWithArgs<MyProfileFragmentArgs>() {

    private val _dataStore = dataStore

    val userEmail by lazy { args.email }

    fun onMyContactsPressed() {
        navigate(MyProfileFragmentDirections.startMyContacts())
    }

    fun clearSavedUserData() {
        viewModelScope.launch(Dispatchers.IO) {
            _dataStore.clearUser()
        }
    }

}