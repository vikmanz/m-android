package com.vikmanz.shpppro.presentation.screens.auth.sign_in

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.vikmanz.shpppro.common.extensions.log
import com.vikmanz.shpppro.common.extensions.swapBoolean
import com.vikmanz.shpppro.data.datastore.Datastore
import com.vikmanz.shpppro.domain.usecases.account.AuthorizeUserUseCase
import com.vikmanz.shpppro.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ua.digitalminds.fortrainerapp.data.result.ApiResult
import javax.inject.Inject

private const val DEFAULT_SHOW_HELPERS = false
@HiltViewModel
class SignInViewModel @Inject constructor(
    dataStore: Datastore,
    private val authorizeUserUseCase : AuthorizeUserUseCase
) : BaseViewModel() {

    private val _dataStore = dataStore

    // Save state of helper buttons. True - visible, False - gone.
    val helperButtonsVisible = MutableLiveData(DEFAULT_SHOW_HELPERS)

    // For show errors only for one field and not for all if it didn't been activated.
    var emailAlreadyFocused = false
    var passwordAlreadyFocused = false

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

    fun startMainActivity(email: String) {
        val direction = SignInFragmentDirections.startMainActivity(email)
        navigateToActivity(direction)
    }

    fun onSignUpClick() {
        val direction = SignInFragmentDirections.startSignUpFragment()
        navigate(direction)
    }

    fun onSignInClick(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            log("Start coroutine")
            authorizeUserUseCase(email, password).collect {

                when (it) {

                    is ApiResult.Loading -> {
                        log("loading")
                    }

                    is ApiResult.Success -> {
                        log("api success")
                        log(it.value.toString())

                        val direction = SignInFragmentDirections.startMainActivity(email)
                        navigateToActivity(direction)

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