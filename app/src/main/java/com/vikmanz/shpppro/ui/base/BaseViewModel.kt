package com.vikmanz.shpppro.ui.base

import androidx.lifecycle.ViewModel
import com.vikmanz.shpppro.navigator.Navigator
import com.vikmanz.shpppro.ui.contacts.ContactsFragment

/**
 * Base class for all view-models.
 */
open class BaseViewModel (): ViewModel() {

//    /**
//     * Override this method in child classes if you want to listen for results
//     * from other screens
//     */
    open fun onResult(result: Any) {

    }
//
//    private val _navigation = MutableLiveData<Event<NavigationCommand>>()
//    val navigation: LiveData<Event<NavigationCommand>> get() = _navigation
//
//    fun navigate(navDirections: NavDirections) {
//        _navigation.value = Event(NavigationCommand.ToDirection(navDirections))
//    }
//
//    fun navigateBack() {
//        _navigation.value = Event(NavigationCommand.Back)
//    }

}