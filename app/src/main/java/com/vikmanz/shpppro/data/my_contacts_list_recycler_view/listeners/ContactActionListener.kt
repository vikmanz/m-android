package com.vikmanz.shpppro.data.my_contacts_list_recycler_view.listeners

import com.vikmanz.shpppro.data.contact_model.Contact
// TODO this is not part of data layer. Move to presentation layer.
/**
 * Interface to send deleteUser from ContactAdapter to MyContactsActivity
 */
interface ContactActionListener {
    fun onTapUser(contactID: Long)
    fun onDeleteUser(contact: Contact)
}