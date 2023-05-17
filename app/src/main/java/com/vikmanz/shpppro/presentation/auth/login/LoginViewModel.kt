package com.vikmanz.shpppro.presentation.auth.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.vikmanz.shpppro.constants.Constants.LOGIN_VIEW_FIRST
import com.vikmanz.shpppro.presentation.base.BaseViewModel
import com.vikmanz.shpppro.presentation.navigator.Navigator
import com.vikmanz.shpppro.presentation.utils.extensions.swapBoolean
import kotlinx.coroutines.launch

private const val DEFAULT_SHOW_HELPERS = false

//TODO looks weird. My intuition suggests that you can avoid parameters for this class
@Suppress("unused")
class LoginViewModel(
    private val navigator: Navigator,
    customArgument: LoginFragment.CustomArgument,
) : BaseViewModel() {

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
        viewModelScope.launch {

        }
        loginScreen.swapBoolean()
    }

    /**
     * Show/hide helpar buttons.
     */
    fun showOrHideHelpers() {
        helperButtonsVisible.swapBoolean()
    }


}