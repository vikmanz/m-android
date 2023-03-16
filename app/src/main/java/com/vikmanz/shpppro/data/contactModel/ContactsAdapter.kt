package com.vikmanz.shpppro.data.contactModel

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.vikmanz.shpppro.databinding.OneContactItemBinding
import com.vikmanz.shpppro.utilits.setContactPhoto
import com.vikmanz.shpppro.utilits.setContactPhotoFromUri

class ContactsAdapter(private val contactActionListener: ContactActionListener) :
    ListAdapter<Contact, ContactsAdapter.ContactHolder>(ContactComparator()) {

    inner class ContactHolder(private val binding: OneContactItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(contact: Contact) {
            with(binding) {
                // bind Photo
                if (contact.contactPhotoUri != null) {
                    ivContactAvatarImage.setContactPhotoFromUri(contact.contactPhotoUri)
                } else {
                    ivContactAvatarImage.setContactPhoto(contact.contactPhotoUrl)
                }
                // bind Name/Career
                tvContactName.text = contact.contactName
                tvContactCareer.text = contact.contactCareer
                // bind delete listener
                btnRemove.setOnClickListener {
                    contactActionListener.onDeleteUser(contact)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactHolder {
        val binding =
            OneContactItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ContactHolder(binding)
    }

    override fun onBindViewHolder(holder: ContactHolder, position: Int) {
        holder.bind(currentList[position])
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

}