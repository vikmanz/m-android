package com.vikmanz.shpppro.myContactsActivity

import android.util.Log
import androidx.lifecycle.ViewModel
import com.vikmanz.shpppro.myContactsActivity.contactModel.Contact
import com.vikmanz.shpppro.myContactsActivity.contactModel.ContactsService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MyContactsViewModel : ViewModel() {

    // створюємо MutableStateFlow зі списком контактів
    private val _contactList = MutableStateFlow(ContactsService().getContacts())



    // створюємо StateFlow, який можна споживати з зовнішнього коду
    var contactList: StateFlow<List<Contact>> = _contactList

    fun addContact(contact: Contact) {
        addContact(contact, _contactList.value.size)
        Log.d("myLog", "New contact created! id:${contact.contactId}, name:${contact.contactName}, contact img counter: ${contact.contactPhotoIndex}.")
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

    fun getFakeContacts() {
        _contactList.value = ContactsService().createFakeContacts()
        phoneListChangedToFake = true
    }

    fun setPhoneContactList(phoneContactList: List<Contact>) {
        _contactList.value = phoneContactList
        phoneListActivated = true
        phoneListChangedToFake = false
    }

    var phoneListActivated = false
    var phoneListChangedToFake = false

}