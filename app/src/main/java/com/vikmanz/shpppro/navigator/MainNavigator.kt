package com.vikmanz.shpppro.navigator

import android.app.Application
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

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

    override fun launch(screen: BaseScreen) = whenActivityActive {
        if (IS_NAVIGATION) launchFragment(it, screen)
            else launchFragment(it, screen, true)

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

    private fun launchFragment(activity: MainActivity, screen: BaseScreen, @Suppress("SameParameterValue") addToBackStack: Boolean = true) {
        val fragment = screen.javaClass.enclosingClass.newInstance() as Fragment
        fragment.arguments = bundleOf(ARG_SCREEN to screen)
        val transaction = activity.supportFragmentManager.beginTransaction()
        if (addToBackStack) transaction.addToBackStack(null)
        transaction
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }

    private fun launchFragment(activity: MainActivity, screen: BaseScreen) {

//        val fragment = screen.javaClass.enclosingClass.newInstance() as Fragment
//        fragment.arguments = bundleOf(ARG_SCREEN to screen)
//        val transaction = activity.supportFragmentManager.beginTransaction()
//        if (addToBackStack) transaction.addToBackStack(null)
//        transaction
//            .replace(R.id.fragmentContainer, fragment)
//            .commit()

        val direction =
            if (screen.name == "hello") HelloFragmentDirections.actionHelloFragmentToEditFragment(screen)
            else EditFragmentDirections.actionEditFragmentToHelloFragment(screen)
        activity.findNavController(R.id.nav_graph).navigate(direction)

    }
}