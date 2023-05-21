package com.vikmanz.shpppro.presentation.navigator

import androidx.lifecycle.AndroidViewModel
import com.vikmanz.shpppro.R
import com.vikmanz.shpppro.presentation.navigator.interfaces.MainNavControllerManager
import com.vikmanz.shpppro.presentation.navigator.interfaces.Navigator
import javax.inject.Inject

/**
 * Navigator implementation. It extends [AndroidViewModel] because 1) we need android dependency
 * (application context); 2) it should survive after screen rotation.
 * https://github.com/romychab/android-tutorials/tree/main/mvvm-navigation
 */
class MainNavigator @Inject constructor(
    mainNavControllerManager: MainNavControllerManager
): Navigator {

    private val navController = mainNavControllerManager.getNavController()

    override fun launchLoginFragment() {  }

    override fun launchMyContacts() = navigateTo(
        direction = R.id.action_profileFragment_to_myContactsFragment
    )

    override fun launchContactDetails() = navigateTo(
        direction = R.id.action_myContactsFragment_to_contactDetailsFragment
    )

    override fun goBack() {
        navController.popBackStack()
    }

    private fun navigateTo(
        direction: Int
    ) {
       // val bundle = bundleOf(getString(R.string.safe_arg_id) to argument)
        navController.navigate(direction)
    }

}