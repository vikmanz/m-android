package com.vikmanz.shpppro.navigator

import android.app.Application
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import com.vikmanz.shpppro.ui.base.BaseArgument
import com.vikmanz.shpppro.Event
import com.vikmanz.shpppro.R
import com.vikmanz.shpppro.constants.Features.USE_NAVIGATION_COMPONENT
import com.vikmanz.shpppro.ui.MainActivity
import com.vikmanz.shpppro.utilits.log

const val ARG_SCREEN = "argument"

/**
 * Navigator implementation. It extends [AndroidViewModel] because 1) we need android dependency
 * (application context); 2) it should survive after screen rotation.
 * https://github.com/romychab/android-tutorials/tree/main/mvvm-navigation
 */
class MainNavigator(
    application: Application
) : AndroidViewModel(application), Navigator {

    val whenActivityActive = MainActivityActions()

    private val _result = MutableLiveData<Event<Any>>()
    val result: LiveData<Event<Any>> = _result

    override fun launchStartFragment(argument: BaseArgument) = launchFragment(argument)

    override fun launchMyContacts(argument: BaseArgument) = launchFragment(
        argument,
        direction = R.id.action_profileFragment_to_myContactsFragment
    )

    override fun launchContactDetails(argument: BaseArgument) = launchFragment(
        argument,
        direction = R.id.action_myContactsFragment_to_contactDetailsFragment
    )

    override fun goBack(result: Any?) = whenActivityActive {
        if (USE_NAVIGATION_COMPONENT) {
           it.findNavController(R.id.fragment_container_main_container).popBackStack()
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
            launchFragmentByNavigation(it, argument, direction)
        } else {
            launchFragmentByTransaction(it, argument)
        }
    }

    private fun launchFragmentByTransaction(
        activity: MainActivity,
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
            .replace(R.id.fragment_container_main_container, fragment)
            .commit()
        log("commit")
    }

    private fun launchFragmentByNavigation(
        activity: MainActivity,
        argument: BaseArgument,
        direction: Int, //NavDirections
    ) {
        val bundle = bundleOf(getString(R.string.safe_arg_id) to argument)
        activity.findNavController(R.id.fragment_container_main_container)
            .navigate(direction, bundle)
    }

}