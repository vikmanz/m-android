package com.vikmanz.shpppro.ui.main.main_fragment.my_contacts_list.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.vikmanz.shpppro.R
import com.vikmanz.shpppro.data.contact_model.Contact
import com.vikmanz.shpppro.databinding.OneContactItemBinding
import com.vikmanz.shpppro.ui.main.main_fragment.my_contacts_list.adapter.listeners.ContactActionListener
import com.vikmanz.shpppro.ui.main.main_fragment.my_contacts_list.adapter.utils.DiffUtilContactsComparator
import com.vikmanz.shpppro.ui.utils.extensions.setGone
import com.vikmanz.shpppro.ui.utils.extensions.setImageWithGlide
import com.vikmanz.shpppro.ui.utils.extensions.setVisible

/**
 * Adapter for Recycler view.
 */
class ContactsAdapter(
    private val contactActionListener: ContactActionListener,
    var isMultiselectMode: Boolean = false
) : ListAdapter<Contact, ContactsAdapter.ContactHolder>(DiffUtilContactsComparator()) {

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

    /**
     * Create Holder for one element.
     */
    inner class ContactHolder(val binding: OneContactItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val multiselectColor = ContextCompat.getColorStateList(
            binding.root.context,
            R.color.multiselect_background_contact_list
        )

        fun bind(contact: Contact) {
            with(binding) {
               // if (isMultiselectMode) bindMultiselectMode()
                imageViewOneContactAvatarImage.setImageWithGlide(contact.contactPhotoLink)
                // bind Name/Career
                textViewOneContactName.text = contact.contactName
                textViewOneContactCareer.text = contact.contactPhone
                // bind delete listener
                buttonOneContactRemove.setOnClickListener {
                    // send contact to MyContactsActivity for delete it from ViewModel.
                    contactActionListener.onDeleteContact(contact)
                }

                root.setOnClickListener {
                    if (isMultiselectMode) {
                        checkboxOneContactMultiSelect.isChecked = !checkboxOneContactMultiSelect.isChecked
                        bindMultiselectMode()
                    }
                    // send contact id to MyContactsActivity for navigate to contact details.
                    contactActionListener.onTapContact(contact)
                }

                root.setOnLongClickListener {
                    isMultiselectMode = !isMultiselectMode
                    if (isMultiselectMode) {
                        contactActionListener.onLongTapContact(contact)
                        bindMultiselectMode()
                        checkboxOneContactMultiSelect.isChecked = true
                    } else {
                        contactActionListener.onLongTapContact(contact)
                        bindNormalMode()
                    }
                    true
                }
            }
        }
        private fun bindMultiselectMode() = with(binding) {
            root.backgroundTintList = multiselectColor
            checkboxOneContactMultiSelect.setVisible()
            buttonOneContactRemove.setGone()
        }
        private fun bindNormalMode() = with(binding) {
            root.backgroundTintList = null
            checkboxOneContactMultiSelect.setGone()
            buttonOneContactRemove.setVisible()
        }
    }

}