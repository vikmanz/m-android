package com.vikmanz.shpppro.presentation.screens.main.add_contact.adapter.diffutil

import androidx.recyclerview.widget.DiffUtil
import com.vikmanz.shpppro.data.model.contact_item.AddContactItem

/**
 * Compare elements in recycler view. Need for ListAdapter type of Recycler view.
 */
class DiffUtilAddContactListItemComparator : DiffUtil.ItemCallback<AddContactItem>() {

    override fun areItemsTheSame(oldItem: AddContactItem, newItem: AddContactItem): Boolean {
        return oldItem.contact.id == newItem.contact.id
    }

    override fun areContentsTheSame(oldItem: AddContactItem, newItem: AddContactItem): Boolean =
        oldItem == newItem

}
