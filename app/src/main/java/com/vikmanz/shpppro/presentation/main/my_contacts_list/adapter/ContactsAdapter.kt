package com.vikmanz.shpppro.presentation.main.my_contacts_list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.vikmanz.shpppro.data.contact_model.Contact
import com.vikmanz.shpppro.presentation.main.my_contacts_list.adapter.listeners.ContactActionListener
import com.vikmanz.shpppro.presentation.main.my_contacts_list.adapter.utils.DiffUtilContactsComparator
import com.vikmanz.shpppro.databinding.OneContactItemBinding
import com.vikmanz.shpppro.presentation.utils.extensions.setImageWithGlide

/**
 * Adapter for Recycler view.
 */
class ContactsAdapter(
    private val contactActionListener: ContactActionListener
) : ListAdapter<Contact, ContactsAdapter.ContactHolder>(DiffUtilContactsComparator()) {

    /**
     * Create Holder for one element.
     */
    inner class ContactHolder(private val binding: OneContactItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(contact: Contact) {
            with(binding) {
                imageViewOneContactAvatarImage.setImageWithGlide(contact.contactPhotoLink)
                // bind Name/Career
                textViewOneContactName.text = contact.contactName
                textViewOneContactCareer.text = contact.contactPhone
                // bind delete listener
                buttonOneContactRemove.setOnClickListener {
                    // send contact to MyContactsActivity for delete it from ViewModel.
                    contactActionListener.onDeleteUser(contact)
                }
                root.setOnClickListener { contactActionListener.onTapUser(contact.contactId) }
            }
        }
    }

    /**
     * Create one element from holder and return it.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactHolder {
        val binding =
            OneContactItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ContactHolder(binding)
    }

    /**
     * Bind info to one element holder.
     */
    override fun onBindViewHolder(holder: ContactHolder, position: Int) {
        holder.bind(currentList[position])
    }
}