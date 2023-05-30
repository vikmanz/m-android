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
    private val contactActionListener: ContactActionListener
) : ListAdapter<Contact, ContactsAdapter.ContactHolder>(DiffUtilContactsComparator()) {

    private var isMultiselectMode = false
    val multiselectList = ArrayList<Contact>()

    /**
     * Create Holder for one element.
     */
    inner class ContactHolder(private val binding: OneContactItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val multiselectColor = ContextCompat.getColorStateList(
            binding.root.context,
            R.color.multiselect_background_contact_list
        )

        fun bind(contact: Contact) {
            with(binding) {
                if (isMultiselectMode) bindMultiselectMode()
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
                    // send contact id to MyContactsActivity for navigate to contact details.
                    if (isMultiselectMode) {
                        multiselectList.add(contact)
                        bindMultiselectMode()
                    } else {
                        contactActionListener.onTapContact(contact.contactId)
                    }
                }

                root.setOnLongClickListener {
                    if (isMultiselectMode) {
                        isMultiselectMode = false
                        bindNormalMode()
                    } else {
                        isMultiselectMode = true
                        bindMultiselectMode()
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
            checkboxOneContactMultiSelect.setVisible()
            buttonOneContactRemove.setGone()
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