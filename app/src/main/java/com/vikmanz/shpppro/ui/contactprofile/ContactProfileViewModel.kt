package com.example.fragmentsnavigatortest.screens.edit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.fragmentsnavigatortest.Event
import com.example.fragmentsnavigatortest.R
import com.example.fragmentsnavigatortest.navigator.Navigator
import com.example.fragmentsnavigatortest.screens.base.BaseViewModel
import com.example.fragmentsnavigatortest.screens.edit.ContactProfileFragment.Screen

class ContactProfileViewModel(
    private val navigator: Navigator,
    screen: Screen
) : BaseViewModel() {

    private val _initialMessageEvent = MutableLiveData<Event<String>>()
    val initialMessageEvent: LiveData<Event<String>> = _initialMessageEvent

    init {
        // sending initial value from screen argument to the fragment
        _initialMessageEvent.value = Event(screen.initialValue)
    }

    fun onSavePressed(message: String) {
        if (message.isBlank()) {
            navigator.toast(R.string.empty_message)
            return
        }
        navigator.goBack(message)
    }

    fun onCancelPressed() {
        navigator.goBack()
    }

}