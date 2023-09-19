package com.vikmanz.shpppro.presentation.screens.main.main_fragment.my_contacts_list.adapter.diffutil

import androidx.recyclerview.widget.DiffUtil
import com.vikmanz.shpppro.data.model.ContactItem

/**
 * Compare elements in recycler view. Need for ListAdapter type of Recycler view.
 */
class DiffUtilContactListItemComparator : DiffUtil.ItemCallback<ContactItem>() {

    override fun areItemsTheSame(oldItem: ContactItem, newItem: ContactItem): Boolean {
        return oldItem.contact.id == newItem.contact.id && oldItem.isChecked == newItem.isChecked
    }

    override fun areContentsTheSame(oldItem: ContactItem, newItem: ContactItem): Boolean =
        oldItem == newItem

}
