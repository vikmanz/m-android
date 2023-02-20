package com.vikmanz.shpppro.myContactsActivity.contactModel
import java.io.Serializable

data class Contact(
    val contactId: Long,
    val contactPhotoUrl: String,
    val contactName: String,
    val contactCareer: String
    ): Serializable


