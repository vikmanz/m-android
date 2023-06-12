package com.vikmanz.shpppro.ui.main.main_fragment.my_contacts_list.adapter.listeners

import com.vikmanz.shpppro.data.model.ContactListItem

/**
 * Interface to send deleteUser from ContactAdapter to MyContactsActivity
 */
interface ContactActionListener {
    fun onTapContact(item: ContactListItem)
    fun onDeleteContact(item: ContactListItem)
    fun onLongTapContact(item: ContactListItem)
}