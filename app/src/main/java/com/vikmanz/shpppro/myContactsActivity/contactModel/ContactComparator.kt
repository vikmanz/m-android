package com.vikmanz.shpppro.myContactsActivity.contactModel

import androidx.recyclerview.widget.DiffUtil

class ContactComparator: DiffUtil.ItemCallback<Contact>() {

    override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean {
        return oldItem.contactId == newItem.contactId
    }

    override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean {
        return oldItem.contactId == newItem.contactId
                && oldItem.contactName == newItem.contactName
                && oldItem.contactCareer == newItem.contactCareer
    }


}
