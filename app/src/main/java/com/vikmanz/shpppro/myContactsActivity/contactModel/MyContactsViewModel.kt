package com.vikmanz.shpppro.myContactsActivity.contactModel

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MyContactsViewModel : ViewModel() {

    // створюємо MutableStateFlow зі списком контактів
    private var _contactList = MutableStateFlow(ContactsService().getContacts())

    // створюємо StateFlow, який можна споживати з зовнішнього коду
    val contactList: StateFlow<MutableList<Contact>> = _contactList

    // додаємо нове ім'я до MutableStateFlow
    fun addContact(contact: Contact) {
        _contactList.value.add(contact)
        val print2 = _contactList.value.size
        Log.d("mylog", "contacts list size: $print2")
    }

    fun deleteContact(contact: Contact) {
        _contactList.value.remove(contact)
    }




}