package com.vikmanz.shpppro.utils.extensions

import android.util.Log
import com.vikmanz.shpppro.constants.Constants.LOG_DEFAULT_MESSAGE
import com.vikmanz.shpppro.constants.Constants.LOG_TAG

/**
 * Function for more comfortable print test messages to console.
 */
fun log(message: String = LOG_DEFAULT_MESSAGE, isDebug: Boolean = true) {
    if (isDebug) Log.d(LOG_TAG, message)
}