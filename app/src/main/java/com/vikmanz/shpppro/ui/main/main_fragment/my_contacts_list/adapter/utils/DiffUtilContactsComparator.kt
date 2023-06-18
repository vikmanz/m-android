package com.vikmanz.shpppro.ui.main.main_fragment.my_contacts_list.adapter.utils

import androidx.recyclerview.widget.DiffUtil
import com.vikmanz.shpppro.data.model.ContactListItemState

/**
 * Compare elements in recycler view. Need for ListAdapter type of Recycler view.
 */
class DiffUtilContactListItemComparator : DiffUtil.ItemCallback<ContactListItemState>() {

    override fun areItemsTheSame(oldItem: ContactListItemState, newItem: ContactListItemState): Boolean {
        return oldItem.contact.contactId == newItem.contact.contactId
    }

    override fun areContentsTheSame(oldItem: ContactListItemState, newItem: ContactListItemState): Boolean =
        oldItem == newItem

}
