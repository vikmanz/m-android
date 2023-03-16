package com.vikmanz.shpppro.data.contactModel

import androidx.recyclerview.widget.DiffUtil

class ContactComparator: DiffUtil.ItemCallback<Contact>() {

    override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean {
        return oldItem.contactId == newItem.contactId
    }

    override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean {
        return oldItem.contactId == newItem.contactId
                && oldItem.contactName == newItem.contactName
                && oldItem.contactPhotoUrl == newItem.contactPhotoUrl
                && oldItem.contactCareer == newItem.contactCareer
    }


}
