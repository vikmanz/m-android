package com.vikmanz.shpppro.data.contact_model

import android.net.Uri
import java.io.Serializable

/**
 * Contact object
 */
data class Contact(
    val contactId: Long,
    val contactPhotoUrl: String,
    val contactPhotoUri: Uri,
    val contactPhotoIndex: Int,
    val contactName: String,
    val contactCareer: String,
    val contactEmail: String,
    val contactPhone: String,
    val contactAddress: String,
    val contactBirthday: String
    ): Serializable


