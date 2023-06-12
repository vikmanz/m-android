package com.vikmanz.shpppro.ui.main.main_fragment.my_contacts_list.adapter.listeners

import com.vikmanz.shpppro.data.model.Contact

/**
 * Interface to send deleteUser from ContactAdapter to MyContactsActivity
 */
interface ContactActionListener {
    fun onTapContact(contact: Contact)
    fun onDeleteContact(contact: Contact)
    fun onLongTapContact(contact: Contact)
}