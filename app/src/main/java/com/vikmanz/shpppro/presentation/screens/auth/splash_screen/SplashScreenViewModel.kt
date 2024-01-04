package com.vikmanz.shpppro.presentation.screens.auth.splash_screen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.vikmanz.shpppro.constants.Constants
import com.vikmanz.shpppro.utils.extensions.log
import com.vikmanz.shpppro.data.datastore.Datastore
import com.vikmanz.shpppro.domain.usecases.account.AuthorizeUserUseCase
import com.vikmanz.shpppro.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ua.digitalminds.fortrainerapp.data.result.ApiResult
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    dataStore: Datastore,
    private val authorizeUserUseCase : AuthorizeUserUseCase
) : BaseViewModel() {

    val login = MutableLiveData("")

    /**
     * Check if user already save login-password, and do autologin if it's need.
     */
    init {
        viewModelScope.launch {
            delay(Constants.SPLASH_DELAY)
            dataStore.userCredentials.collect { credentials ->
                credentials?.let { login(credentials.userEmail, credentials.userPassword) } ?: startLoginFragment()
            }
        }
    }

    private fun login(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            log("Start coroutine")
            authorizeUserUseCase(email, password).collect {

                when (it) {

                    is ApiResult.Loading -> {
                        log("loading")
                    }

                    is ApiResult.Success -> {
                        log("api success")
                        val direction = SplashScreenFragmentDirections.startMainActivity()
                        navigateToActivity(direction)
                    }

                    is ApiResult.NetworkError -> {
                        log("api network error!")
                        startLoginFragment()
                    }

                    is ApiResult.ServerError -> {
                        log("api server error!")
                        startLoginFragment()
                    }
                }
            }
            log("End coroutine")
        }
    }

    private fun startLoginFragment() {
        val direction = SplashScreenFragmentDirections.startSignInFragment()
        navigate(direction)
    }

}