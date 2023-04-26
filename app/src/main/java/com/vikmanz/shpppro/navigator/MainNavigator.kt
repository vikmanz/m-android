package com.vikmanz.shpppro.navigator

import android.app.Application
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.vikmanz.shpppro.ui.base.BaseArgs
import com.vikmanz.shpppro.Event
import com.vikmanz.shpppro.R
import com.vikmanz.shpppro.ui.MainActivity
import com.vikmanz.shpppro.utilits.log

const val ARG_SCREEN = "SCREEN"
const val IS_NAVIGATION = true

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

    override fun launchMyProfile(baseArgs: BaseArgs) = whenActivityActive {
        log("launch my profile enter point")
        launchFragment(it, baseArgs)
    }

    override fun launchMyContacts(baseArgs: BaseArgs) = whenActivityActive {
        launchFragment(it, baseArgs)
    }

    override fun goBack(result: Any?) = whenActivityActive {
        if (result != null) {
            _result.value = Event(result)
        }
        it.onBackPressed()
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
        screen: BaseArgs,
        @Suppress("SameParameterValue") addToBackStack: Boolean = true
    ) {
        log("launch fragment method")
        val fragment = screen.javaClass.enclosingClass.newInstance() as Fragment
        fragment.arguments = bundleOf(ARG_SCREEN to screen)
        val transaction = activity.supportFragmentManager.beginTransaction()
        if (addToBackStack) transaction.addToBackStack(null)
        log("begin transaction")
        transaction
            .replace(R.id.fragment_container_main_container, fragment)
            .commit()
        log("commit")
    }

   // private fun launchFragment(activity: MainActivity, screen: BaseArgs) {

//        val fragment = screen.javaClass.enclosingClass.newInstance() as Fragment
//        fragment.arguments = bundleOf(ARG_SCREEN to screen)
//        val transaction = activity.supportFragmentManager.beginTransaction()
//        if (addToBackStack) transaction.addToBackStack(null)
//        transaction
//            .replace(R.id.fragmentContainer, fragment)
//            .commit()
//
//        val direction =
//            if (screen.name == "hello") HelloFragmentDirections.actionHelloFragmentToEditFragment(screen)
//            else EditFragmentDirections.actionEditFragmentToHelloFragment(screen)
//        activity.findNavController(R.id.nav_graph).navigate(direction)

    //}
}