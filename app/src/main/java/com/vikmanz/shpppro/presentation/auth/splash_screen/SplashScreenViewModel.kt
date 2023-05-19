package com.vikmanz.shpppro.presentation.auth.splash_screen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.vikmanz.shpppro.App
import com.vikmanz.shpppro.constants.Constants
import com.vikmanz.shpppro.presentation.auth.login.LoginFragment
import com.vikmanz.shpppro.presentation.base.BaseViewModel
import com.vikmanz.shpppro.presentation.navigator.Navigator
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreenViewModel(
    private val navigator: Navigator,
    @Suppress("UNUSED_PARAMETER") customArgument: SplashScreenFragment.CustomArgument
) : BaseViewModel() {

    private val dataStore = App.dataStore
    val login = MutableLiveData("")

    /**
     * Check if user already save login-password, and do autologin if it's need.
     */
    init {
        viewModelScope.launch {
            delay(Constants.SPLASH_DELAY)
            dataStore.userNameFlow.collect { name ->
                if (name.isBlank()) goToLoginFragment() else login.value = name
            }
        }
    }

    private fun goToLoginFragment() {
        navigator.launchLoginFragment(LoginFragment.CustomArgument())
    }

}