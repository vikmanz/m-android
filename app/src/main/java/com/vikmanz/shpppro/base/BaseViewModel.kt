package com.vikmanz.shpppro.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import com.vikmanz.shpppro.ui.navigator.NavigationCommand
import com.vikmanz.shpppro.utilits.Event

abstract class BaseViewModel : ViewModel() {

    private val _navigation = MutableLiveData<Event<NavigationCommand>>()

    val navigation: LiveData<Event<NavigationCommand>> get() = _navigation

    fun navigate(navDirections: NavDirections) {
        _navigation.value = Event(NavigationCommand.ToDirection(navDirections))
    }

    fun navigateToActivity(navDirections: NavDirections) {
        _navigation.value = Event(NavigationCommand.ToActivity(navDirections))
    }

    fun navigateBack() {
        _navigation.value = Event(NavigationCommand.Back)
    }

}