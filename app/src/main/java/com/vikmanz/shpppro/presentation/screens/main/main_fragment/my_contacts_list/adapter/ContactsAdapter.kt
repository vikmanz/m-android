package com.vikmanz.shpppro.presentation.screens.main.main_fragment.my_contacts_list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.vikmanz.shpppro.data.model.ContactItem
import com.vikmanz.shpppro.databinding.MyContactListItemBinding
import com.vikmanz.shpppro.databinding.MyContactListMultiselectItemBinding
import com.vikmanz.shpppro.presentation.screens.main.main_fragment.my_contacts_list.adapter.diffutil.DiffUtilContactListItemComparator
import com.vikmanz.shpppro.presentation.screens.main.main_fragment.my_contacts_list.adapter.view_holders.MultiselectContactViewHolder
import com.vikmanz.shpppro.presentation.screens.main.main_fragment.my_contacts_list.adapter.view_holders.SimpleContactViewHolder
import com.vikmanz.shpppro.presentation.screens.main.main_fragment.my_contacts_list.adapter.view_holders.interfaces.ContactViewHolder
import com.vikmanz.shpppro.presentation.utils.extensions.alsoLog

/**
 * Adapter for Recycler view.
 */
class ContactsAdapter(
) : ListAdapter<ContactItem, ContactViewHolder>(
    DiffUtilContactListItemComparator()
) {

    var isMultiselect: Boolean = false

    private enum class ViewType {
        NORMAL, MULTISELECT
    }

    override fun getItemViewType(position: Int): Int {
        return if (isMultiselect) ViewType.MULTISELECT.ordinal else ViewType.NORMAL.ordinal
    }

    /**
     * Create one element from holder and return it.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder =
        when (viewType) {

            ViewType.NORMAL.ordinal -> {
                val binding =
                    MyContactListItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                SimpleContactViewHolder(binding)
            }

            ViewType.MULTISELECT.ordinal -> {
                val binding =
                    MyContactListMultiselectItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                MultiselectContactViewHolder(binding)
            }

            else -> throw Exception("Unknown viewType of viewHolder!")
        }


    /**
     * Bind info to one element holder.
     */
    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

}