package com.vikmanz.shpppro.presentation.screens.main.add_contact.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.vikmanz.shpppro.data.model.ContactItem
import com.vikmanz.shpppro.databinding.AddContactListItemBinding
import com.vikmanz.shpppro.presentation.screens.main.main_fragment.my_contacts_list.adapter.diffutil.DiffUtilContactListItemComparator
import com.vikmanz.shpppro.presentation.utils.extensions.alsoLog
import com.vikmanz.shpppro.presentation.utils.extensions.setImageWithGlide

/**
 * Adapter for Recycler view.
 */
class ContactsAdapter(
) : ListAdapter<ContactItem, ContactsAdapter.AddContactViewHolder>(
    DiffUtilContactListItemComparator()
) {

    /**
     * Create one element from holder and return it.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddContactViewHolder {
        val binding =
            AddContactListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return AddContactViewHolder(binding)
    }


    /**
     * Bind info to one element holder.
     */
    override fun onBindViewHolder(holder: AddContactViewHolder, position: Int) {
        holder.bind(currentList[position])
    }


    inner class AddContactViewHolder(
        private val binding: AddContactListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(contactItem: ContactItem) {
            with(binding) {

                // Bind info
                imageViewOneContactAvatarImage.setImageWithGlide(contactItem.contact.image)
                textViewOneContactName.text = contactItem.contact.email
                textViewOneContactCareer.text = contactItem.contact.name

                // Bind onClick listeners
                buttonOneContactRemove.setOnClickListener { contactItem.onDelete(contactItem) alsoLog "click on item bin" }
                root.setOnClickListener { contactItem.onClick(contactItem) alsoLog "click on item" }
                root.setOnLongClickListener { true.also { contactItem.onLongClick(contactItem) alsoLog "long click on item" } }
            }
        }
    }
}