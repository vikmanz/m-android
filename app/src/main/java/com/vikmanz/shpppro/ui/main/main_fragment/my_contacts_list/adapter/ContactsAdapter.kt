package com.vikmanz.shpppro.ui.main.main_fragment.my_contacts_list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.vikmanz.shpppro.R
import com.vikmanz.shpppro.data.model.ContactListItemState
import com.vikmanz.shpppro.databinding.OneContactItemBinding
import com.vikmanz.shpppro.ui.main.main_fragment.my_contacts_list.adapter.listeners.ContactActionListener
import com.vikmanz.shpppro.ui.main.main_fragment.my_contacts_list.adapter.utils.DiffUtilContactListItemComparator
import com.vikmanz.shpppro.ui.utils.extensions.setGone
import com.vikmanz.shpppro.ui.utils.extensions.setImageWithGlide
import com.vikmanz.shpppro.ui.utils.extensions.setVisible

/**
 * Adapter for Recycler view.
 */
class ContactsAdapter(
    private val contactActionListener: ContactActionListener
) : ListAdapter<ContactListItemState, ContactsAdapter.ContactViewHolder>(
    DiffUtilContactListItemComparator()
) {

    var isMultiselect: Boolean = false

    /**
     * Create one element from holder and return it.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val binding =
            OneContactItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ContactViewHolder(binding)
    }

    /**
     * Bind info to one element holder.
     */
    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    /**
     * Create Holder for one element.
     */
    inner class ContactViewHolder(private val binding: OneContactItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val multiselectColor = ContextCompat.getColorStateList(
            binding.root.context,
            R.color.multiselect_background_contact_list
        )

        fun bind(contactState: ContactListItemState) {
            bindContactInfo(contactState)
            bindDeleteIcon(contactState)
            decorateHolder(contactState)
            bindOnClicks(contactState)
        }

        private fun bindOnClicks(contactState: ContactListItemState) = with(binding) {
            // bind onClick
            root.setOnClickListener {
                if (isMultiselect) {
                    contactState.onCheck()
                } else {
                    contactActionListener.onTapContact(contactState.contact.contactId)
                }
            }

            // bind onLong Click
            root.setOnLongClickListener {
                if (!isMultiselect) contactState.onCheck()
                true
            }
        }

        private fun bindDeleteIcon(contactState: ContactListItemState) = with(binding) {
            buttonOneContactRemove.setOnClickListener {
                // send contact to MyContactsActivity for delete it from ViewModel.
                contactActionListener.onDeleteContact(contactState.contact)
            }
        }

        private fun bindContactInfo(contactState: ContactListItemState) = with(binding) {
            imageViewOneContactAvatarImage.setImageWithGlide(contactState.contact.contactPhotoLink)
            textViewOneContactName.text = contactState.contact.contactName
            textViewOneContactCareer.text = contactState.contact.contactPhone
        }

        private fun decorateHolder(contactState: ContactListItemState) = with(binding) {
            if (isMultiselect) {
                root.backgroundTintList = multiselectColor
                checkboxOneContactMultiSelect.setVisible()
                checkboxOneContactMultiSelect.isChecked = contactState.contact.isChecked
                buttonOneContactRemove.setGone()
            } else {
                root.backgroundTintList = null
                checkboxOneContactMultiSelect.setGone()
                buttonOneContactRemove.setVisible()
            }
        }

    }
}