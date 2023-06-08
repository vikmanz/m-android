package com.vikmanz.shpppro.ui.auth.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.vikmanz.shpppro.ui.base.BaseViewModel
import com.vikmanz.shpppro.constants.Constants.LOGIN_VIEW_FIRST
import com.vikmanz.shpppro.data.datastore.interfaces.MyPreferences
import com.vikmanz.shpppro.ui.auth.splash_screen.SplashScreenFragmentDirections
import com.vikmanz.shpppro.utils.extensions.swapBoolean
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val DEFAULT_SHOW_HELPERS = false
@HiltViewModel
class LoginViewModel @Inject constructor(
    dataStore: MyPreferences
) : BaseViewModel() {

    private val _dataStore = dataStore

    // Save state of screen layout. True - Login screen, False - Register screen.
    val loginScreen = MutableLiveData(LOGIN_VIEW_FIRST)

    // Save state of helper buttons. True - visible, False - gone.
    val helperButtonsVisible = MutableLiveData(DEFAULT_SHOW_HELPERS)

    // For show errors only for one field and not for all if it didn't been activated.
    var emailAlreadyFocused = false
    var passwordAlreadyFocused = false

    /**
     * Change screen to register or to login.
     */
    fun swapLoginAndRegister() {
        loginScreen.swapBoolean()
    }

    /**
     * Show/hide helper buttons.
     */
    fun showOrHideHelpers() {
        helperButtonsVisible.swapBoolean()
    }

    /**
     *  Save user data from text input fields and language key from class variable to Data Store.
     */
    fun saveUserEmailToDatastore(email: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _dataStore.saveUserSata(email)
        }
    }

    fun startMainActivity(email: String) {
        val direction = SplashScreenFragmentDirections.startMainActivity(email)
        navigateToActivity(direction)
    }

}