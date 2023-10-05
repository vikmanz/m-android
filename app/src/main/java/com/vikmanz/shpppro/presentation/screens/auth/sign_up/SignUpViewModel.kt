package com.vikmanz.shpppro.presentation.screens.auth.sign_up

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.vikmanz.shpppro.common.Constants.LOGIN_VIEW_FIRST
import com.vikmanz.shpppro.common.extensions.log
import com.vikmanz.shpppro.common.extensions.swapBoolean
import com.vikmanz.shpppro.data.datastore.Datastore
import com.vikmanz.shpppro.domain.usecases.account.RegisterUserUseCase
import com.vikmanz.shpppro.presentation.base.BaseViewModel
import com.vikmanz.shpppro.presentation.screens.auth.splash_screen.SplashScreenFragmentDirections
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ua.digitalminds.fortrainerapp.data.result.ApiResult
import javax.inject.Inject

private const val DEFAULT_SHOW_HELPERS = false
@HiltViewModel
class SignUpViewModel @Inject constructor(
    dataStore: Datastore,
    private val registerUserUseCase: RegisterUserUseCase
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
    fun saveUserEmailToDatastore(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _dataStore.saveUserSata(email, password)
        }
    }

    fun onSignInClick() {
        val direction = SignUpFragmentDirections.startSignInFragment()
        navigate(direction)
    }

    fun onRegisterClick(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            log("Start coroutine")
            registerUserUseCase(email, password).collect {

                when (it) {

                    is ApiResult.Loading -> {
                        log("loading")
                    }

                    is ApiResult.Success -> {
                        log("api success")
                        log(it.value.toString())
                        val direction = SignUpFragmentDirections.startSignUpExtended()
                        navigate(direction)
                    }

                    is ApiResult.NetworkError -> {
                        log("api network error!")
                    }

                    is ApiResult.ServerError -> {
                        log("api server error!")
                    }
                }
            }
            log("End coroutine")
        }
    }
}