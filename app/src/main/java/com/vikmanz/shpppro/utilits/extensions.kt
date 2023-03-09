package com.vikmanz.shpppro.utilits

import android.content.Context
import android.util.TypedValue
import java.util.*

/**
 * Extra function of String class, for replace the first char of String to Upper case.
 */
fun String.firstCharToUpperCase() = replaceFirstChar {
    if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
}



