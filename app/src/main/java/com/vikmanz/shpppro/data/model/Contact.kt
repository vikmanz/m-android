package com.vikmanz.shpppro.data.model

import java.io.Serializable

/**
 * Contact object
 */
data class Contact(
    val contactId: Long,
    val contactPhotoLink: Any, //todo
    val contactPhotoIndex: Int,
    val contactName: String,
    val contactCareer: String,
    val contactEmail: String,
    val contactPhone: String,
    val contactAddress: String,
    val contactBirthday: String,
    var isChecked: Boolean = false, //todo
): Serializable


