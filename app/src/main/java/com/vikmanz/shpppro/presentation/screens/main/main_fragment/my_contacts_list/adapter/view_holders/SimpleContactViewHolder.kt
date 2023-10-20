package com.vikmanz.shpppro.presentation.screens.main.main_fragment.my_contacts_list.adapter.view_holders

import com.vikmanz.shpppro.data.model.contact_item.ContactItem
import com.vikmanz.shpppro.databinding.MyContactListItemBinding
import com.vikmanz.shpppro.presentation.screens.main.main_fragment.my_contacts_list.adapter.view_holders.interfaces.ContactViewHolder
import com.vikmanz.shpppro.presentation.utils.extensions.alsoLog
import com.vikmanz.shpppro.presentation.utils.extensions.setImageWithGlide
import com.vikmanz.shpppro.presentation.utils.extensions.setVisibleOrGone
import com.vikmanz.shpppro.utils.extensions.log

class SimpleContactViewHolder(
    private val binding: MyContactListItemBinding
) : ContactViewHolder(binding.root) {
    override fun bind(contactItem: ContactItem) {
        with(binding) {
            with(contactItem) {

                // Bind info
                imageViewOneContactAvatarImage.setImageWithGlide(contact.image)
                textViewOneContactName.text = contact.email
                textViewOneContactCareer.text = contact.name

                // Bind buttons
                buttonOneContactRemove.setVisibleOrGone(!isError && !isLoading)
                buttonOneContactError.setVisibleOrGone(isError)
                log("inside adapter loading -> $isLoading")
                progressBarMyContacts.setVisibleOrGone(isLoading)

                // Bind onClick listeners
                buttonOneContactRemove.setOnClickListener { contactItem.onDelete(contactItem) alsoLog "click on item bin" }
                buttonOneContactError.setOnClickListener { contactItem.onDelete(contactItem) alsoLog "click on error button" }
                root.setOnClickListener { contactItem.onClick(contactItem) alsoLog "click on item" }
                root.setOnLongClickListener { true.also { contactItem.onLongClick(contactItem) alsoLog "long click on item" } }
            }
        }
    }
}