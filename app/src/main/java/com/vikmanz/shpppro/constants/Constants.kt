package com.vikmanz.shpppro.constants

/**
 * Constants of application. Contains settings and keys for intent.
 */
object Constants {

    /**
     * Auth:
     */
    // Splash screen delay
    const val SPLASH_DELAY = 0L
    // Minimum number of chars in password. Don't forget check max password length in Integer.
    const val MIN_PASSWORD_LENGTH = 8

    /**
     * My Contacts:
     */
    // Undo dialog show length when contact was deleted.
    const val SNACK_BAR_VIEW_TIME = 5000L

    /**
     * LOG
     */
    const val LOG_TAG = "myLog"
    const val LOG_DEFAULT_MESSAGE = "default message"

    /**
     * Server settings
     */
    const val BASE_URL = "http://178.63.9.114:7777/api/"

    /**
     * Recycler view
     */
    // Margin of elements in recycler view (left, right, bottom).
    const val MARGINS_OF_ELEMENTS = 20
}