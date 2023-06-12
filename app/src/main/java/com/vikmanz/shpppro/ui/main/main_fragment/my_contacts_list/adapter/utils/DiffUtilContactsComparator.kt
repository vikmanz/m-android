package com.vikmanz.shpppro.ui.main.main_fragment.my_contacts_list.adapter.utils

import androidx.recyclerview.widget.DiffUtil
import com.vikmanz.shpppro.data.model.Contact
import com.vikmanz.shpppro.data.model.ContactListItem

/**
 * Compare elements in recycler view. Need for ListAdapter type of Recycler view.
 */
class DiffUtilContactListItemComparator : DiffUtil.ItemCallback<ContactListItem>() {

    override fun areItemsTheSame(oldItem: ContactListItem, newItem: ContactListItem): Boolean {
        return oldItem.contact.contactId == newItem.contact.contactId
    }

    override fun areContentsTheSame(oldItem: ContactListItem, newItem: ContactListItem): Boolean =
        oldItem == newItem

}
