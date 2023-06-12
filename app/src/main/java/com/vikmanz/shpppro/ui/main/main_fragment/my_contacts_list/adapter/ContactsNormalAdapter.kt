package com.vikmanz.shpppro.ui.main.main_fragment.my_contacts_list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.vikmanz.shpppro.R
import com.vikmanz.shpppro.data.model.ContactListItem
import com.vikmanz.shpppro.databinding.OneContactItemBinding
import com.vikmanz.shpppro.ui.main.main_fragment.my_contacts_list.adapter.listeners.ContactActionListener
import com.vikmanz.shpppro.ui.main.main_fragment.my_contacts_list.adapter.utils.DiffUtilContactListItemComparator
import com.vikmanz.shpppro.ui.utils.extensions.setGone
import com.vikmanz.shpppro.ui.utils.extensions.setImageWithGlide
import com.vikmanz.shpppro.ui.utils.extensions.setVisible

/**
 * Adapter for Recycler view.
 */
class ContactsNormalAdapter(
    private val contactActionListener: ContactActionListener
) : ListAdapter<ContactListItem, ContactsNormalAdapter.ContactHolder>(DiffUtilContactListItemComparator()) {

    var isMultiselect: Boolean = false

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
    inner class ContactHolder(private val binding: OneContactItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val multiselectColor = ContextCompat.getColorStateList(
            binding.root.context,
            R.color.multiselect_background_contact_list
        )

        fun bind(contactListItem: ContactListItem) {
            with(binding) {

                // if (isMultiselectMode) bindMultiselectMode()
                imageViewOneContactAvatarImage.setImageWithGlide(contactListItem.contact.contactPhotoLink)
                // bind Name/Career
                textViewOneContactName.text = contactListItem.contact.contactName
                textViewOneContactCareer.text = contactListItem.contact.contactPhone
                // bind delete listener
                buttonOneContactRemove.setOnClickListener {
                    // send contact to MyContactsActivity for delete it from ViewModel.
                    contactActionListener.onDeleteContact(contactListItem)
                }

                // Multiselect decorations
                if (isMultiselect) {
                    root.backgroundTintList = multiselectColor
                    checkboxOneContactMultiSelect.setVisible()
                    checkboxOneContactMultiSelect.isChecked = contactListItem.isChecked
                    buttonOneContactRemove.setGone()
                } else {
                    root.backgroundTintList = null
                    checkboxOneContactMultiSelect.setGone()
                    buttonOneContactRemove.setVisible()
                }

                root.setOnClickListener {
                    if (isMultiselect) {
                        checkboxOneContactMultiSelect.isChecked =
                            !checkboxOneContactMultiSelect.isChecked
                    }
                    // send contact id to MyContactsActivity for navigate to contact details.
                    contactActionListener.onTapContact(contactListItem)
                }

                root.setOnLongClickListener {
                    contactActionListener.onLongTapContact(contactListItem)
                    true
                }
            }
        }
    }

}