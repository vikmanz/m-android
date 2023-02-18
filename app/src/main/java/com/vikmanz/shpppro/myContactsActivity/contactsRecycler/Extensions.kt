package com.vikmanz.shpppro.myContactsActivity.contactsRecycler

import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.vikmanz.shpppro.R

// Glide options
val GLIDE_OPTIONS = RequestOptions()
    .centerCrop()
    .circleCrop()
    .placeholder(R.drawable.ic_person)
    .error(R.drawable.ic_person)
    .diskCacheStrategy(DiskCacheStrategy.ALL)
    .priority(Priority.HIGH)

// Extensions for set photo from url.
fun AppCompatImageView.setContactPhoto(contactPhotoUrl: String) {
    Glide.with(context)
        .load(contactPhotoUrl)
        .apply(GLIDE_OPTIONS)
        .into(this)
}

class Extensions {
}


