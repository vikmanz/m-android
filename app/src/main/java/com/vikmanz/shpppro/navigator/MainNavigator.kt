package com.vikmanz.shpppro.navigator

import android.app.Application
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import com.vikmanz.shpppro.ui.base.BaseArgument
import com.vikmanz.shpppro.Event
import com.vikmanz.shpppro.R
import com.vikmanz.shpppro.constants.Constants
import com.vikmanz.shpppro.constants.Preferences.USE_NAVIGATION_COMPONENT
import com.vikmanz.shpppro.ui.MainActivity
import com.vikmanz.shpppro.ui.my_profile.MyProfileFragment
import com.vikmanz.shpppro.ui.my_profile.MyProfileFragmentDirections
import com.vikmanz.shpppro.utilits.log

const val ARG_SCREEN = "SCREEN"

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

    override fun launchStartFragment(argument: BaseArgument) = whenActivityActive {
        log("launch my profile enter point by transaction")

//        val argument =
//            MyProfileFragment.CustomArgument(getString(R.string.main_activity_person_email_hardcoded))
        launchFragment(it, argument)
    }


    override fun launchMyContacts(argument: BaseArgument) = whenActivityActive {
        if (USE_NAVIGATION_COMPONENT) {
            val direction = MyProfileFragmentDirections.actionProfileFragmentToMyContactsFragment(argument)
            log("launch my contacts in main navigator")
            launchFragmentViaNavigation(it, direction, argument)
        } else {
            launchFragment(it, argument)
        }
    }

    override fun goBack(result: Any?) = whenActivityActive {
        if (USE_NAVIGATION_COMPONENT) {
            //TODO need impl

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

    private fun launchFragmentViaNavigation(
        activity: MainActivity,
        direction: NavDirections,
        argument: BaseArgument
    ) {
        activity.findNavController(R.id.fragment_container_main_container).navigate(direction)
    }

}