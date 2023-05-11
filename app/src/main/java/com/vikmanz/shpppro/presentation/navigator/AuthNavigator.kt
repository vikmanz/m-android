package com.vikmanz.shpppro.presentation.navigator

import android.app.Application
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import com.vikmanz.shpppro.presentation.base.BaseArgument
import com.vikmanz.shpppro.R
import com.vikmanz.shpppro.constants.USE_NAVIGATION_COMPONENT
import com.vikmanz.shpppro.presentation.auth.AuthActivity
import com.vikmanz.shpppro.utilits.extensions.log

/**
 * Navigator implementation. It extends [AndroidViewModel] because 1) we need android dependency
 * (application context); 2) it should survive after screen rotation.
 * https://github.com/romychab/android-tutorials/tree/main/mvvm-navigation
 */
class AuthNavigator(
    application: Application
) : AndroidViewModel(application), Navigator {

    val whenActivityActive = MainActivityActions()

    private val _result = MutableLiveData<Event<Any>>()
    val result: LiveData<Event<Any>> = _result

    override fun launchStartFragment(argument: BaseArgument) = launchFragment(argument)

    override fun launchLoginFragment(argument: BaseArgument) {
        log("LLF in auth navigator")
        launchFragment(
            argument,
            direction = R.id.action_splashScreenFragment_to_loginFragment
        )
    }

    override fun launchMyContacts(argument: BaseArgument) {    }
    override fun launchContactDetails(argument: BaseArgument) {    }

    override fun goBack(result: Any?) = whenActivityActive {
        if (USE_NAVIGATION_COMPONENT) {
           it.findNavController(R.id.fragmentContainer_authActivity).popBackStack()
        } else {
            if (result != null) {
                _result.value = Event(result)
            }
            @Suppress("DEPRECATION")
            it.onBackPressed()
        }
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
        if (USE_NAVIGATION_COMPONENT) {
            //val direction = MyProfileFragmentDirections.actionProfileFragmentToMyContactsFragment(argument)
            launchFragmentByNavigation(it as AuthActivity, argument, direction)
        } else {
            launchFragmentByTransaction(it as AuthActivity, argument)
        }
    }

    private fun launchFragmentByTransaction(
        activity: AuthActivity,
        argument: BaseArgument,
        @Suppress("SameParameterValue") addToBackStack: Boolean = true
    ) {
        log("launch fragment method")
        val fragment = argument.javaClass.enclosingClass.newInstance() as Fragment
        fragment.arguments = bundleOf(ARG_SCREEN to argument)
        val transaction = activity.supportFragmentManager.beginTransaction()
        if (addToBackStack) transaction.addToBackStack(null)
        log("begin transaction")
        transaction
            .replace(R.id.fragmentContainer_authActivity, fragment)
            .commit()
        log("commit")
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