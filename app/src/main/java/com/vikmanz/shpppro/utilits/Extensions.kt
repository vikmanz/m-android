package com.vikmanz.shpppro.utilits

import android.net.Uri
import android.util.Log
import java.util.*
import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.vikmanz.shpppro.R

/**
 * Extra function of String class, for replace the first char of String to Upper case.
 */
fun String.firstCharToUpperCase() = replaceFirstChar {
    if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
}


/**
 * Extra function of ImageView class, for change photo via Glide from photo URL (from internet).
 */
fun AppCompatImageView.setContactPhoto(contactPhotoUrl: String) {
    Glide.with(context)
        .load(contactPhotoUrl)
        .apply(GLIDE_OPTIONS)
        .into(this)
}

/**
 * Extra function of ImageView class, for change photo via Glide from photo URI (from gallery).
 */
fun AppCompatImageView.setContactPhotoFromUri(uri: Uri?) {
    Glide.with(context)
        .load(uri)
        .apply(GLIDE_OPTIONS)
        .into(this)
}

/**
 * Settings of Glide.
 */
val GLIDE_OPTIONS = RequestOptions()
    .centerCrop()
    .circleCrop()
    .placeholder(R.drawable.ic_person)
    .error(R.drawable.ic_person)
    .diskCacheStrategy(DiskCacheStrategy.ALL)
    .priority(Priority.HIGH)

/**
 * Function for more comfortable print test messages to console.
 */
fun log(message: String) {
    Log.d("myLog", message)
}

