package com.example.fragmentsnavigatortest.screens.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import com.vikmanz.shpppro.Event
import com.vikmanz.shpppro.navigator.NavigationCommand

/**
 * Base class for all view-models.
 */
open class BaseViewModel : ViewModel() {

//    /**
//     * Override this method in child classes if you want to listen for results
//     * from other screens
//     */
//    open fun onResult(result: Any) {
//
//    }

    private val _navigation = MutableLiveData<Event<NavigationCommand>>()
    val navigation: LiveData<Event<NavigationCommand>> get() = _navigation

    fun navigate(navDirections: NavDirections) {
        _navigation.value = Event(NavigationCommand.ToDirection(navDirections))
    }

    fun navigateBack() {
        _navigation.value = Event(NavigationCommand.Back)
    }

}