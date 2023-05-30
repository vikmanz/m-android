package com.vikmanz.shpppro.ui.main.main_fragment.my_profile

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
    savedStateHandle: SavedStateHandle,
    dataStore: MyPreferences,
) : BaseViewModel() {

    private val navArgs = MyProfileFragmentArgs.fromSavedStateHandle(savedStateHandle)
    private val _dataStore = dataStore

    var userEmail = navArgs.email //"hard.codedviewmodel@gm.com" //

    fun clearSavedUserData() {
        viewModelScope.launch(Dispatchers.IO) {
            _dataStore.clearUser()
        }
    }

}