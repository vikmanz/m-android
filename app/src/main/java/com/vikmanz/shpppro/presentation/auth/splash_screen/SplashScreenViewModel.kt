package com.vikmanz.shpppro.presentation.auth.splash_screen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vikmanz.shpppro.constants.Constants
import com.vikmanz.shpppro.data.datastore.interfaces.MyPreferences
import com.vikmanz.shpppro.presentation.navigator.Navigator
import com.vikmanz.shpppro.utilits.extensions.log
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class SplashScreenViewModel @Inject constructor(
    navigator: Navigator,
    dataStore: MyPreferences
) : ViewModel() {

    val login = MutableLiveData("")
    private val _navigator = navigator

    /**
     * Check if user already save login-password, and do autologin if it's need.
     */
    init {
        viewModelScope.launch {
            delay(Constants.SPLASH_DELAY)
            dataStore.userName.collect { name ->
                if (name.isBlank()) goToLoginFragment() else login.value = name
            }
        }
    }

    private fun goToLoginFragment() {
        log("DDD")
        _navigator.launchLoginFragment()
    }

}