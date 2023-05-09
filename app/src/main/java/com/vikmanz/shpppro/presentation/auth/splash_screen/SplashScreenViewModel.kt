package com.vikmanz.shpppro.presentation.auth.splash_screen

import com.vikmanz.shpppro.presentation.auth.login.LoginFragment
import com.vikmanz.shpppro.presentation.base.BaseViewModel
import com.vikmanz.shpppro.presentation.navigator.Navigator
import com.vikmanz.shpppro.utilits.extensions.log

class SplashScreenViewModel(
    private val navigator: Navigator,
    customArgument: SplashScreenFragment.CustomArgument
) : BaseViewModel() {

    fun goToLoginFragment() {
        log("call go to login fragment")
        navigator.launchLoginFragment(LoginFragment.CustomArgument())
    }

}