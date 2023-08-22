package com.vikmanz.shpppro.presentation.screens.auth.sign_up_extended

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.vikmanz.shpppro.common.Constants.LOGIN_VIEW_FIRST
import com.vikmanz.shpppro.common.extensions.log
import com.vikmanz.shpppro.common.extensions.swapBoolean
import com.vikmanz.shpppro.common.model.User
import com.vikmanz.shpppro.data.datastore.PreferencesDatastore
import com.vikmanz.shpppro.domain.usecases.account.EditUserUseCase
import com.vikmanz.shpppro.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ua.digitalminds.fortrainerapp.data.result.ApiResult
import javax.inject.Inject

private const val DEFAULT_SHOW_HELPERS = false

@HiltViewModel
class SignUpExtendedViewModel @Inject constructor(
    dataStore: PreferencesDatastore,
    savedStateHandle: SavedStateHandle,
    private val editUserUseCase: EditUserUseCase
) : BaseViewModel() {

    private val navArgs = SignUpExtendedFragmentArgs.fromSavedStateHandle(savedStateHandle)

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

    fun onSaveClick(username: String, phone: String) {

        val account = navArgs.account
        val user = User(
            id = account.user.id,
            email = account.user.email,
            name = username,
            phone = phone
        )

        viewModelScope.launch(Dispatchers.IO) {
            log("Start coroutine")
            editUserUseCase(
                token = account.accessToken,
                user = user
            ).collect {

                when (it) {

                    is ApiResult.Loading -> {
                        log("loading")
                    }

                    is ApiResult.Success -> {
                        log("api success")
                        log(it.value.toString())

                        val direction =
                            SignUpExtendedFragmentDirections.startMainActivity(user.email.toString())
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


    fun onButtonBackPressed() {
        navigateBack()
    }

}