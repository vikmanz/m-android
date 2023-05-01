package com.vikmanz.shpppro.presentation.utils.extensions

import java.util.Locale

/**
 * Extra function of String class, for replace the first char of String to Upper case.
 */
fun String.firstCharToUpperCase() = replaceFirstChar {
    if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
}
