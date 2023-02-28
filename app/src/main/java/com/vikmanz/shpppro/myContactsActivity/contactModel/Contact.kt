package com.vikmanz.shpppro.myContactsActivity.contactModel
import java.io.Serializable

data class Contact(
    val contactId: Long,
    val contactPhotoUrl: String,
    val contactPhotoIndex: Int,
    val contactName: String,
    val contactCareer: String,
    val contactEmail: String,
    val contactPhone: String,
    val contactAddress: String,
    val contactBirthday: String
    ): Serializable


