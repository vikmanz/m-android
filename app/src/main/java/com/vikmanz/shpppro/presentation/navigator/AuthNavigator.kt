package com.vikmanz.shpppro.presentation.navigator

import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.navigation.NavController
import com.vikmanz.shpppro.R
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

/**
 * Navigator implementation. It extends [AndroidViewModel] because 1) we need android dependency
 * (application context); 2) it should survive after screen rotation.
 * https://github.com/romychab/android-tutorials/tree/main/mvvm-navigation
 */
class AuthNavigator @Inject constructor(
    @ActivityContext val context: Context,
    navController: NavController
) : Navigator {

    private val _navController = navController

    override fun launchLoginFragment() {
        navigateTo(
            direction = R.id.action_splashScreenFragment_to_loginFragment
        )
    }
    override fun launchMyContacts() {    }
    override fun launchContactDetails() {    }

    override fun goBack() {
        _navController.popBackStack()
    }

    private fun navigateTo(
        direction: Int, //NavDirections
    ) {
        _navController.navigate(direction)
    }
}