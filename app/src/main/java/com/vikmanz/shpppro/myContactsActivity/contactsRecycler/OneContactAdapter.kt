package com.vikmanz.shpppro.myContactsActivity.contactsRecycler

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vikmanz.shpppro.R
import com.vikmanz.shpppro.databinding.OneContactItemBinding
import com.vikmanz.shpppro.myContactsActivity.contactModel.OneContact


class OneContactAdapter: RecyclerView.Adapter<OneContactAdapter.OneContactHolder>() {

    var contactList = mutableListOf<OneContact>()

    class OneContactHolder(item: View): RecyclerView.ViewHolder(item) {

        private val binding = OneContactItemBinding.bind(item)

        @Suppress("UNUSED_PARAMETER")
        fun bind(oneContact: OneContact, context: Context) {
            with (binding) {

                // bind Photo
                ivContactAvatarImage.setContactPhoto(oneContact.contactPhotoUrl)

                // bind Name/Career
                tvContactName.text = oneContact.contactName
                tvContactCareer.text = oneContact.contactCareer
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OneContactHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.one_contact_item, parent, false)
        return OneContactHolder(view)
    }

    override fun onBindViewHolder(holder: OneContactHolder, position: Int) {
        holder.bind(contactList[position], holder.itemView.context)

    }

    override fun getItemCount(): Int {
        return contactList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addContact(oneContact: OneContact) {
        contactList.add(oneContact)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun removeContact(oneContact: OneContact) {
        // Find contact in list
        val indexToDelete: Int = contactList.indexOfFirst { it.contactId == oneContact.contactId }
        if (indexToDelete == -1) return // Останавливаемся, если не находим такого человека
        else contactList.removeAt(indexToDelete) // Удаляем человека
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun changeContactsToPhoneBook(contactsBook: MutableList<OneContact>) {
        contactList = contactsBook
        notifyDataSetChanged()
    }

}