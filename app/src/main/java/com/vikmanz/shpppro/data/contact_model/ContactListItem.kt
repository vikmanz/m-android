package com.vikmanz.shpppro.data.contact_model

import java.io.Serializable

data class ContactListItem(
    val contactId: Long,
    val contactPhotoLink: Any,
    val contactPhotoIndex: Int,
    val contactName: String,
    val contactCareer: String,
    val contactEmail: String,
    val contactPhone: String,
    val contactAddress: String,
    val contactBirthday: String,
    var isChecked: Boolean
): Serializable
