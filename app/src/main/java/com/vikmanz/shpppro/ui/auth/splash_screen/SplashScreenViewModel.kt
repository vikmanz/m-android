package com.vikmanz.shpppro.ui.auth.splash_screen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.vikmanz.shpppro.base.BaseViewModel
import com.vikmanz.shpppro.constants.Constants
import com.vikmanz.shpppro.data.datastore.interfaces.MyPreferences
import com.vikmanz.shpppro.utilits.extensions.log
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    dataStore: MyPreferences
) : BaseViewModel() {

    val login = MutableLiveData("")

    /**
     * Check if user already save login-password, and do autologin if it's need.
     */
    init {
        viewModelScope.launch {
            delay(Constants.SPLASH_DELAY)
            dataStore.userName.collect { email ->
                if (email.isBlank()) startLoginFragment() else startMainActivity(email)
            }
        }
    }

    private fun startLoginFragment() {
       log("Go to login fragment")
        val direction = SplashScreenFragmentDirections.startLoginFragment()
        navigate(direction)
    }

    private fun startMainActivity(email: String) {
        log("Go to main activity")
        val direction = SplashScreenFragmentDirections.startMainActivity(email)
        navigate(direction)
    }
}