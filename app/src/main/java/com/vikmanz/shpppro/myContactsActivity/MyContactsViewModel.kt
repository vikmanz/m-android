package com.vikmanz.shpppro.myContactsActivity

import androidx.lifecycle.ViewModel
import com.vikmanz.shpppro.myContactsActivity.contactModel.Contact
import com.vikmanz.shpppro.myContactsActivity.contactModel.ContactsService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MyContactsViewModel : ViewModel() {

    // створюємо MutableStateFlow зі списком контактів
    private var _contactList = MutableStateFlow(ContactsService().getContacts())

    // створюємо StateFlow, який можна споживати з зовнішнього коду
    val contactList: StateFlow<List<Contact>> = _contactList

    // додаємо нове ім'я до MutableStateFlow
    fun addContact(contact: Contact) {
        addContact(contact, _contactList.value.size)
    }

    fun addContact(contact: Contact, index: Int) {
        _contactList.value = _contactList.value.toMutableList().apply { add(index, contact) }
    }

    fun deleteContact(contact: Contact) {
        _contactList.value = _contactList.value.toMutableList().apply { remove(contact) }
    }

    fun getContactPosition(contact: Contact) : Int{
        return _contactList.value.indexOf(contact)
    }

    fun getContact(index: Int) : Contact{
        return _contactList.value[index]
    }

    fun getContactsFromPhonebook() {
        _contactList = MutableStateFlow(ContactsService().getPhonebook())
    }

}