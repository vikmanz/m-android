package com.vikmanz.shpppro.constants

/**
 * Constants of application. Contains settings and keys for intent.
 */
object Constants {

    /**
     * Settings:
     */
    // Login (true) or Register (false) view first.
    const val LOGIN_VIEW_FIRST = true
    // Show or hide helper buttons on start.
    const val VIEW_HELP_BUTTONS_ON_CREATE = false
    // Minimum number of chars in password. Don't forget check max password length in Integer.
    const val MIN_PASSWORD_LENGTH = 8

    // My Contacts
    // Minimum number of chars in password.
    const val START_NUMBER_OF_CONTACTS = 5
    const val SNACK_BAR_VIEW_TIME = 5000
    const val MARGINS_OF_ELEMENTS = 20
    // FakeData (true) or PhoneData (false) view first on myContacts
    const val FAKE_FIRST = true


    /**
     * Intent Keys. Don't change it.
     */
    const val INTENT_EMAIL_ID = "email_identifier"
    const val INTENT_LANG_ID = "language_identifier"
}