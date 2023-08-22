package com.vikmanz.shpppro.presentation.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import com.vikmanz.shpppro.presentation.navigator.Event
import com.vikmanz.shpppro.presentation.navigator.NavigationCommand

abstract class BaseViewModel : ViewModel() {

    private val _navigation = MutableLiveData<Event<NavigationCommand>>()

    val navigation: LiveData<Event<NavigationCommand>> get() = _navigation

    fun navigate(navDirections: NavDirections) {
//        _navigation.value = Event(NavigationCommand.ToDirection(navDirections))
        _navigation.postValue(Event(NavigationCommand.ToDirection(navDirections)))
    }

    fun navigateToActivity(navDirections: NavDirections) {
//        _navigation.value = Event(NavigationCommand.ToActivity(navDirections))
        _navigation.postValue(Event(NavigationCommand.ToActivity(navDirections)))
    }

    fun navigateBack() {
//        _navigation.value = Event(NavigationCommand.Back)
        _navigation.postValue(Event(NavigationCommand.Back))
    }

}