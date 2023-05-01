package com.vikmanz.shpppro.presentation.utils.extensions

import android.net.Uri
import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.vikmanz.shpppro.R

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
fun AppCompatImageView.setContactPhotoFromUri(uri: Uri) {
    Glide.with(context)
        .load(uri)
        .apply(GLIDE_OPTIONS)
        .into(this)
}

