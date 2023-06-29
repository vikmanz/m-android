package com.vikmanz.shpppro.ui.main.main_fragment.my_profile

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.vikmanz.shpppro.ui.base.BaseViewModel
import com.vikmanz.shpppro.data.source.local.interfaces.PreferencesDatastore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyProfileViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    dataStore: PreferencesDatastore,
) : BaseViewModel() {

    private val navArgs = MyProfileFragmentArgs.fromSavedStateHandle(savedStateHandle)
    private val _dataStore = dataStore

    var userEmail = navArgs.email //"hard.codedviewmodel@gm.com" //

    fun clearSavedUserData() {

        //todo
        viewModelScope.launch {
            _dataStore.clearUser()
        }
    }

}