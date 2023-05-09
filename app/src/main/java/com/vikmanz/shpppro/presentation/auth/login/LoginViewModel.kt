package com.vikmanz.shpppro.presentation.auth.login

import androidx.lifecycle.MutableLiveData
import com.vikmanz.shpppro.constants.Constants.LOGIN_VIEW_FIRST
import com.vikmanz.shpppro.presentation.base.BaseViewModel
import com.vikmanz.shpppro.presentation.navigator.Navigator
import com.vikmanz.shpppro.presentation.utils.extensions.swapBoolean

private const val DEFAULT_SHOW_HELPERS = false
class LoginViewModel(
    private val navigator: Navigator,
    customArgument: LoginFragment.CustomArgument
) : BaseViewModel() {

    // Save state of screen layout. True - Login screen, False - Register screen.
    val loginScreen = MutableLiveData(LOGIN_VIEW_FIRST)

    // Save state of helper buttons. True - visible, False - gone.
    // Save state of screen layout. True - Login screen, False - Register screen.
    val helperButtonsVisible = MutableLiveData(DEFAULT_SHOW_HELPERS)

    var emailAlreadyFocused = false
    var passwordAlreadyFocused = false


    /**
     * Change screen to register or to login.
     */
    fun swapLoginAndRegister() {
        loginScreen.swapBoolean()
    }

    /**
     * Show/hide helpar buttons.
     */
    fun showOrHideHelpers() {
        helperButtonsVisible.swapBoolean()
    }


}