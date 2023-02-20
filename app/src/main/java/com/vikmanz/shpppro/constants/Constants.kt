package com.vikmanz.shpppro.constants

/**
 * Constants of application. Contains settings and keys for intent, save/load state and Data Store.
 */
object Constants {

    // Settings
    const val LOGIN_VIEW_FIRST = true               // Login (true) or Register (false) view first.
    const val VIEW_HELP_BUTTONS_ON_CREATE = false   // Show or hide helper buttons on start.
    const val MIN_PASSWORD_LENGTH = 8               // Minimum number of chars in password.

    // My Contacts
    const val START_NUMBER_OF_CONTACTS = 5            // Minimum number of chars in password.
    const val SNACK_BAR_VIEW_TIME = 5000
    const val MARGINS_OF_ELEMENTS = 20

    // Intent Keys. Don't need to change.
    const val INTENT_EMAIL_ID = "email_identifier"
    const val INTENT_LANG_ID = "language_identifier"

    // Save/Load State Keys. Don't need to change.
    const val EMAIL_FIELD_STATE_KEY = "EMAIL_KEY"
    const val PASSWORD_FIELD_STATE_KEY = "PASSWORD_KEY"
    const val PASSWORD_VIEW_STATE_KEY = "PASSWORD_VIEW_KEY"
    const val CHECKBOX_STATE_STATE_KEY = "CHECKBOX_KEY"
    const val LANGUAGE_STATE_KEY = "LAND_ID_KEY"
    const val HELP_BUTTONS_STATE_KEY = "HELP_BUTTONS_KEY"
    const val LANGUAGE_STATE_KEY_TWO = "LAND_ID_KEY_TWO"

    // Data Store Keys. Don't need to change.
    const val DS_USER_NAME = "user_name"
    const val DS_USER_PASSWORD = "user_password"
    const val DS_USER_AUTOLOGIN_STATUS = "user_login_status"
    const val DS_USER_LANGUAGE_STATUS = "user_language_status"

}