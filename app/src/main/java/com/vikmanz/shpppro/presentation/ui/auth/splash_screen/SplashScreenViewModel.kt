package com.vikmanz.shpppro.presentation.ui.auth.splash_screen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.vikmanz.shpppro.common.Constants
import com.vikmanz.shpppro.data.datastore.PreferencesDatastore
import com.vikmanz.shpppro.presentation.ui.base.BaseViewModel
import com.vikmanz.shpppro.ui.auth.splash_screen.SplashScreenFragmentDirections
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    dataStore: PreferencesDatastore
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
        val direction = SplashScreenFragmentDirections.startLoginFragment()
        navigate(direction)
    }

    private fun startMainActivity(email: String) {
        val direction = SplashScreenFragmentDirections.startMainActivity(email)
        navigateToActivity(direction)
    }
}