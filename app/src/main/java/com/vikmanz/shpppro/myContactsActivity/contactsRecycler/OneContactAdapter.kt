package com.vikmanz.shpppro.myContactsActivity.contactsRecycler

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vikmanz.shpppro.R
import com.vikmanz.shpppro.databinding.OneContactItemBinding

class OneContactAdapter: RecyclerView.Adapter<OneContactAdapter.OneContactHolder>() {

    val contactList = ArrayList<OneContact>()

    class OneContactHolder(item: View): RecyclerView.ViewHolder(item) {
        val binding = OneContactItemBinding.bind(item)
        fun bind(oneContact: OneContact) {
            with (binding) {
                ivContactAvatarImage.setImageResource(oneContact.imageId)
                tvContactName.text = oneContact.personName
                tvContactCareer.text = oneContact.personCareer
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OneContactHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.one_contact_item, parent, false)
        return OneContactHolder(view)
    }

    override fun onBindViewHolder(holder: OneContactHolder, position: Int) {
        holder.bind(contactList[position])
    }

    override fun getItemCount(): Int {
        return contactList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addContact(oneContact: OneContact) {
        contactList.add(oneContact)
        notifyDataSetChanged()
    }

}