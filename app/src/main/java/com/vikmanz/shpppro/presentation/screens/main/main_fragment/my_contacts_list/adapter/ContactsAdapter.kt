package com.vikmanz.shpppro.presentation.screens.main.main_fragment.my_contacts_list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.vikmanz.shpppro.R
import com.vikmanz.shpppro.common.model.ContactItem
import com.vikmanz.shpppro.databinding.OneContactItemBinding
import com.vikmanz.shpppro.presentation.screens.main.main_fragment.my_contacts_list.adapter.diffutil.DiffUtilContactListItemComparator
import com.vikmanz.shpppro.presentation.screens.main.main_fragment.my_contacts_list.adapter.listeners.ContactActionListener
import com.vikmanz.shpppro.presentation.utils.extensions.setGone
import com.vikmanz.shpppro.presentation.utils.extensions.setImageWithGlide
import com.vikmanz.shpppro.presentation.utils.extensions.setVisible

/**
 * Adapter for Recycler view.
 */
class ContactsAdapter(
    private val contactActionListener: ContactActionListener
) : ListAdapter<ContactItem, ContactsAdapter.ContactViewHolder>(
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

        fun bind(contactState: ContactItem) {
            bindContactInfo(contactState)
            bindDeleteIcon(contactState)
            decorateHolder(contactState)
            bindOnClicks(contactState)
        }

        private fun bindOnClicks(contactState: ContactItem) = with(binding) {
            // bind onClick
            root.setOnClickListener {
                if (isMultiselect) {
                    contactState.onCheck()
                } else {
                    contactActionListener.onTapContact(contactState.contact.id)
                }
            }

            // bind onLong Click
            root.setOnLongClickListener {
                if (!isMultiselect) contactState.onCheck()
                true
            }
        }

        private fun bindDeleteIcon(contact: ContactItem) = with(binding) {
            buttonOneContactRemove.setOnClickListener {
                // send contact to MyContactsActivity for delete it from ViewModel.
                contactActionListener.onDeleteContact(contact)
            }
        }

        private fun bindContactInfo(contact: ContactItem) = with(binding) {
            imageViewOneContactAvatarImage.setImageWithGlide(contact.contact.image)
            textViewOneContactName.text = contact.contact.email
            textViewOneContactCareer.text = contact.contact.name
        }

        private fun decorateHolder(contact: ContactItem) = with(binding) {
            if (isMultiselect) {
                root.backgroundTintList = multiselectColor
                checkboxOneContactMultiSelect.setVisible()
                checkboxOneContactMultiSelect.isChecked = contact.isChecked
                buttonOneContactRemove.setGone()
            } else {
                root.backgroundTintList = null
                checkboxOneContactMultiSelect.setGone()
                buttonOneContactRemove.setVisible()
            }
        }

    }
}