package com.vikmanz.shpppro.myContactsActivity.contactModel

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vikmanz.shpppro.R
import com.vikmanz.shpppro.databinding.OneContactItemBinding
import com.vikmanz.shpppro.myContactsActivity.setContactPhoto
import kotlinx.coroutines.*


@OptIn(DelicateCoroutinesApi::class)
@SuppressLint("NotifyDataSetChanged")
class ContactsAdapter(
    private val viewModel: MyContactsViewModel
    ): RecyclerView.Adapter<ContactsAdapter.OneContactHolder>() {

    var contactList = mutableListOf<Contact>()
    val scope = CoroutineScope(Job() + Dispatchers.Main)

    init {
        // створюємо coroutine для збору даних з StateFlow
        GlobalScope.launch {
            viewModel.contactList.collect { contactList ->
                withContext(Dispatchers.Main) {
                    this@ContactsAdapter.contactList = contactList as MutableList<Contact>
                    notifyDataSetChanged()
                   // Log.d("mylog", "UPDATED!")
                }
            }
        }
    }

    class OneContactHolder(item: View): RecyclerView.ViewHolder(item) {

        private val binding = OneContactItemBinding.bind(item)

        @Suppress("UNUSED_PARAMETER")
        fun bind(contact: Contact, context: Context) {
            with (binding) {

                // bind Photo
                ivContactAvatarImage.setContactPhoto(contact.contactPhotoUrl)

                // bind Name/Career
                tvContactName.text = contact.contactName
                tvContactCareer.text = contact.contactCareer
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
    fun addContact(contact: Contact) {
        viewModel.addContact(contact)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun removeContact(contact: Contact) {
        // Find contact in list
        val indexToDelete: Int = contactList.indexOfFirst { it.contactId == contact.contactId }
        if (indexToDelete == -1) return // Останавливаемся, если не находим такого человека
        else contactList.removeAt(indexToDelete) // Удаляем человека
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun changeContactsToPhoneBook(contactsBook: MutableList<Contact>) {
        contactList = contactsBook
        notifyDataSetChanged()
    }

}