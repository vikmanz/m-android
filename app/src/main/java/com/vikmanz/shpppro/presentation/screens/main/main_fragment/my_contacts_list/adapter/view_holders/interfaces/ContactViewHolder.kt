package com.vikmanz.shpppro.presentation.screens.main.main_fragment.my_contacts_list.adapter.view_holders.interfaces

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.vikmanz.shpppro.data.model.ContactItem

abstract class ContactViewHolder (
    val view: View
) : RecyclerView.ViewHolder(view) {

    abstract fun bind(contactItem: ContactItem)

}