package com.vikmanz.shpppro.presentation.utils.extensions

import android.net.Uri
import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.vikmanz.shpppro.R
import com.vikmanz.shpppro.data.utils.RandomPhotoTaker
import java.net.URL
import java.security.InvalidParameterException

/**
 * Settings of Glide.
 */
val GLIDE_OPTIONS = RequestOptions()
    .centerCrop()
    .circleCrop()
    .placeholder(R.drawable.icon_person)
    .error(R.drawable.icon_person)
    .diskCacheStrategy(DiskCacheStrategy.ALL)
    .priority(Priority.HIGH)

/**
 * Extra function of ImageView class, for change photo via Glide from link.
 * Link can be:
 * - URI (from gallery),
 * - photo URL (from internet),
 * - resource id (from resources).
 *
 * @param link URI, URL or resource id.
 */
fun AppCompatImageView.setImageWithGlide(link: Any? = null) {
   //if (link is Uri || link is URL || link is Int || link is String)
        Glide.with(context)
            .load(link ?: RandomPhotoTaker.getRandomPhoto())
            .apply(GLIDE_OPTIONS)
            .into(this)
   // else throw InvalidParameterException()
}
