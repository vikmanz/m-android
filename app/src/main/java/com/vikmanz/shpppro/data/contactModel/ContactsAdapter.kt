package com.vikmanz.shpppro.data.contactModel

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.vikmanz.shpppro.databinding.OneContactItemBinding
import com.vikmanz.shpppro.utilits.setContactPhoto
import com.vikmanz.shpppro.utilits.setContactPhotoFromUri

/**
 * Adapter for Recycler view.
 */
class ContactsAdapter(private val contactActionListener: ContactActionListener) :
    ListAdapter<Contact, ContactsAdapter.ContactHolder>(DiffUtilContactsComparator()) {

    /**
     * Create Holder for one element.
     */
    inner class ContactHolder(private val binding: OneContactItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(contact: Contact) {
            with(binding) {
                // bind Photo
                if (contact.contactPhotoUri == null) {
                    ivContactAvatarImage.setContactPhoto(contact.contactPhotoUrl)
                } else {
                    ivContactAvatarImage.setContactPhotoFromUri(contact.contactPhotoUri)
                }
                // bind Name/Career
                tvContactName.text = contact.contactName
                tvContactCareer.text = contact.contactCareer
                // bind delete listener
                btnRemove.setOnClickListener {
                    // send contact to MyContactsActivity for delete it from ViewModel.
                    contactActionListener.onDeleteUser(contact)
                }
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