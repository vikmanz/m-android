package com.vikmanz.shpppro.presentation.screens.main.main_fragment.my_contacts_list.adapter.listeners

import com.vikmanz.shpppro.common.model.ContactItem

/**
 * Interface to send deleteUser from ContactAdapter to MyContactsActivity
 */
interface ContactActionListener {
    fun onTapContact(contactId: Int)
    fun onDeleteContact(contact: ContactItem)
}