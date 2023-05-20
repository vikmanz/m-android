package com.vikmanz.shpppro.presentation.navigator

import android.app.Application
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.AndroidViewModel
import androidx.navigation.findNavController
import com.vikmanz.shpppro.R
import com.vikmanz.shpppro.presentation.auth.AuthActivity
import com.vikmanz.shpppro.presentation.base.BaseArgument

/**
 * Navigator implementation. It extends [AndroidViewModel] because 1) we need android dependency
 * (application context); 2) it should survive after screen rotation.
 * https://github.com/romychab/android-tutorials/tree/main/mvvm-navigation
 */
class AuthNavigator(
    application: Application
) : AndroidViewModel(application), Navigator {

    val whenActivityActive = ActivityActions()

    override fun launchStartFragment(argument: BaseArgument) = launchFragment(argument)

    override fun launchLoginFragment(argument: BaseArgument) {
        launchFragment(
            argument,
            direction = R.id.action_splashScreenFragment_to_loginFragment
        )
    }

    override fun launchMyContacts(argument: BaseArgument) {    }
    override fun launchContactDetails(argument: BaseArgument) {    }

    override fun goBack(result: Any?) = whenActivityActive {
           it.findNavController(R.id.fragmentContainer_authActivity).popBackStack()
    }

    override fun onCleared() {
        super.onCleared()
        whenActivityActive.clear()
    }

    override fun toast(messageRes: Int) {
        Toast.makeText(getApplication(), messageRes, Toast.LENGTH_SHORT).show()
    }

    override fun getString(messageRes: Int): String {
        return getApplication<Application>().getString(messageRes)
    }

    private fun launchFragment(
        argument: BaseArgument,
        direction: Int = -1
    ) = whenActivityActive {
            launchFragmentByNavigation(it as AuthActivity, argument, direction)
    }

    private fun launchFragmentByNavigation(
        activity: AuthActivity,
        argument: BaseArgument,
        direction: Int, //NavDirections
    ) {
        val bundle = bundleOf(getString(R.string.safe_arg_id) to argument)
        activity.findNavController(R.id.fragmentContainer_authActivity)
            .navigate(direction, bundle)
    }

    companion object {
        const val ARG_SCREEN = "argument"
    }
}