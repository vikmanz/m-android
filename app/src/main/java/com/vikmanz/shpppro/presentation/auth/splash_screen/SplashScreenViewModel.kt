package com.vikmanz.shpppro.presentation.auth.splash_screen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vikmanz.shpppro.constants.Constants
import com.vikmanz.shpppro.data.datastore.interfaces.MyPreferences
import com.vikmanz.shpppro.presentation.navigator.interfaces.Navigator
import com.vikmanz.shpppro.utilits.extensions.log
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    private val navigator: Navigator,
    dataStore: MyPreferences
) : ViewModel() {

    val login = MutableLiveData("")
   // private val _navigator = navigator

    /**
     * Check if user already save login-password, and do autologin if it's need.
     */
    init {
        viewModelScope.launch {
            delay(Constants.SPLASH_DELAY)
            dataStore.userName.collect { name ->
                if (name.isBlank()) goToLoginFragment() else login.value = name
                log(name.toString())
            }
        }
    }

    private fun goToLoginFragment() {
       log("DDD")
       navigator.launchLoginFragment()
    }

}