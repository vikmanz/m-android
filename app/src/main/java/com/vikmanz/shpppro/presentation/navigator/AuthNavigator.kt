package com.vikmanz.shpppro.presentation.navigator

import androidx.lifecycle.AndroidViewModel
import com.vikmanz.shpppro.R
import com.vikmanz.shpppro.presentation.navigator.interfaces.AuthNavControllerManager
import com.vikmanz.shpppro.presentation.navigator.interfaces.Navigator
import javax.inject.Inject

/**
 * Navigator implementation. It extends [AndroidViewModel] because 1) we need android dependency
 * (application context); 2) it should survive after screen rotation.
 * https://github.com/romychab/android-tutorials/tree/main/mvvm-navigation
 */
class AuthNavigator @Inject constructor(
    authNavControllerManager: AuthNavControllerManager
) : Navigator {

    private val navController = authNavControllerManager.getNavController()

    override fun launchLoginFragment() {
        navigateTo(
            direction = R.id.action_splashScreenFragment_to_loginFragment
        )
    }
    override fun launchMyContacts() {    }
    override fun launchContactDetails() {    }

    override fun goBack() {
        navController.popBackStack()
    }

    private fun navigateTo(
        direction: Int, //NavDirections
    ) {
        navController.navigate(direction)
    }
}