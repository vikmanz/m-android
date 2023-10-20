package com.vikmanz.shpppro.presentation.screens.main.main_fragment.my_contacts_list.adapter.view_holders

import com.vikmanz.shpppro.data.model.contact_item.ContactItem
import com.vikmanz.shpppro.databinding.MyContactListItemBinding
import com.vikmanz.shpppro.presentation.screens.main.main_fragment.my_contacts_list.adapter.view_holders.interfaces.ContactViewHolder
import com.vikmanz.shpppro.presentation.utils.extensions.alsoLog
import com.vikmanz.shpppro.presentation.utils.extensions.setImageWithGlide

class SimpleContactViewHolder(
    private val binding: MyContactListItemBinding
) : ContactViewHolder(binding.root) {
    override fun bind(contactItem: ContactItem) {
        with(binding) {

            // Bind info
            imageViewOneContactAvatarImage.setImageWithGlide(contactItem.contact.image)
            textViewOneContactName.text = contactItem.contact.email
            textViewOneContactCareer.text = contactItem.contact.name

            // Bind onClick listeners
            buttonOneContactRemove.setOnClickListener { contactItem.onDelete(contactItem) alsoLog "click on item bin" }
            root.setOnClickListener { contactItem.onClick(contactItem) alsoLog "click on item" }
            root.setOnLongClickListener { true.also { contactItem.onLongClick(contactItem) alsoLog "long click on item" } }
        }
    }
}