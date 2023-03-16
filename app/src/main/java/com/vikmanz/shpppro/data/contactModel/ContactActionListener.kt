package com.vikmanz.shpppro.data.contactModel

/**
 * Interface to send deleteUser from ContactAdapter to MyContactsActivity
 */
interface ContactActionListener {
    fun onDeleteUser(contact: Contact)
}