package com.vikmanz.shpppro.presentation.main.my_contacts_list.adapter.utils

import androidx.recyclerview.widget.DiffUtil
import com.vikmanz.shpppro.data.contact_model.Contact

/**
 * Compare elements in recycler view. Need for ListAdapter type of Recycler view.
 */
class DiffUtilContactsComparator: DiffUtil.ItemCallback<Contact>() {

    override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean {
        return oldItem.contactId == newItem.contactId
    }

    override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean = oldItem == newItem
}
