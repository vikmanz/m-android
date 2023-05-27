package com.vikmanz.shpppro.data.contact_model.utils

import com.vikmanz.shpppro.data.contact_model.Contact
import com.vikmanz.shpppro.data.contact_model.ContactListItem

fun Contact.toContactListItem() : ContactListItem {
    return with(this) {
        ContactListItem(
            contactId = contactId,
            contactPhotoLink = contactPhotoLink,
            contactPhotoIndex = contactPhotoIndex,
            contactName = contactName,
            contactCareer = contactCareer,
            contactEmail = contactEmail,
            contactPhone = contactPhone,
            contactAddress = contactAddress,
            contactBirthday = contactBirthday,
            isChecked = false
        )
    }
}