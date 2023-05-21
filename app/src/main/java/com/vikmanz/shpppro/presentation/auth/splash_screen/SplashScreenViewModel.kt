package com.vikmanz.shpppro.presentation.auth.splash_screen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vikmanz.shpppro.constants.Constants
import com.vikmanz.shpppro.data.datastore.interfaces.MyPreferences
import com.vikmanz.shpppro.presentation.navigator.Navigator
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class SplashScreenViewModel @Inject constructor(
    private val navigator: Navigator,
    private val dataStore: MyPreferences
) : ViewModel() {

    val login = MutableLiveData("")

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
        navigator.launchLoginFragment()
    }

}