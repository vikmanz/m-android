package com.vikmanz.shpppro.presentation.screens.main.main_fragment.my_contacts_list.adapter.view_holders

import com.vikmanz.shpppro.data.model.ContactItem
import com.vikmanz.shpppro.databinding.MyContactListMultiselectItemBinding
import com.vikmanz.shpppro.presentation.screens.main.main_fragment.my_contacts_list.adapter.view_holders.interfaces.ContactViewHolder
import com.vikmanz.shpppro.presentation.utils.extensions.alsoLog
import com.vikmanz.shpppro.presentation.utils.extensions.setImageWithGlide

class MultiselectContactViewHolder(
    private val binding: MyContactListMultiselectItemBinding
) : ContactViewHolder(binding.root) {

    override fun bind(contactItem: ContactItem) {
        with(binding) {

            // Bind info
            imageViewOneContactAvatarImage.setImageWithGlide(contactItem.contact.image)
            textViewOneContactName.text = contactItem.contact.email
            textViewOneContactCareer.text = contactItem.contact.name
            checkboxOneContactMultiSelect.isChecked = contactItem.isChecked

            // Bind onClick listeners
            root.setOnClickListener { contactItem.onClick(contactItem) alsoLog "click on item in MS"  }
        }
    }

}