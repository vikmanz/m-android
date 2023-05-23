package com.vikmanz.shpppro.ui.main.my_contacts_list.adapter.listeners

import com.vikmanz.shpppro.data.contact_model.Contact

/**
 * Interface to send deleteUser from ContactAdapter to MyContactsActivity
 */
interface ContactActionListener {
    fun onTapUser(contactID: Long)
    fun onDeleteUser(contact: Contact)
}