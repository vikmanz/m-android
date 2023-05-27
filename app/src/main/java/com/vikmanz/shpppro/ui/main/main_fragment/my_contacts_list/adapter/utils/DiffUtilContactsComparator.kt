package com.vikmanz.shpppro.ui.main.main_fragment.my_contacts_list.adapter.utils

import androidx.recyclerview.widget.DiffUtil
import com.vikmanz.shpppro.data.contact_model.ContactListItem

/**
 * Compare elements in recycler view. Need for ListAdapter type of Recycler view.
 */
class DiffUtilContactsComparator: DiffUtil.ItemCallback<ContactListItem>() {

    override fun areItemsTheSame(oldItem: ContactListItem, newItem: ContactListItem): Boolean {
        return oldItem.contactId == newItem.contactId
    }

    override fun areContentsTheSame(oldItem: ContactListItem, newItem: ContactListItem): Boolean = oldItem == newItem

}
