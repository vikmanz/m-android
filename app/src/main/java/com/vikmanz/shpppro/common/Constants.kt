package com.vikmanz.shpppro.common

/**
 * Constants of application. Contains settings and keys for intent.
 */
object Constants {

    /**
     * Auth:
     */
    // Splash screen delay
    const val SPLASH_DELAY = 0L
    // Login (true) or Register (false) view first.
    const val LOGIN_VIEW_FIRST = true
    // Minimum number of chars in password. Don't forget check max password length in Integer.
    const val MIN_PASSWORD_LENGTH = 8

    /**
     * My Contacts:
     */
    // Minimum number of chars in password.
    const val START_NUMBER_OF_CONTACTS = 8
    // Undo dialog show length when contact was deleted.
    const val SNACK_BAR_VIEW_TIME = 5000
    // Margin of elements in recycler view (left, right, bottom).
    const val MARGINS_OF_ELEMENTS = 20
    // Minimum number of contacts, which will be imported from phonebook.
    const val MAX_PHONE_IMPORT_CONTACTS_COUNT = 10

    /**
     * LOG
     */
    const val LOG_TAG = "myLog"
    const val LOG_DEFAULT_MESSAGE = "default message"

    /**
     * Server settings
     */
    const val BASE_URL = "http://178.63.9.114:7777/api/"
}