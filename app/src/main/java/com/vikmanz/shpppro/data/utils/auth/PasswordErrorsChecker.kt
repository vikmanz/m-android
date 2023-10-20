package com.vikmanz.shpppro.data.utils.auth

import androidx.fragment.app.Fragment
import com.vikmanz.shpppro.R
import com.vikmanz.shpppro.constants.Constants

/**
 * Class which get full email and parse email to "Name Surname".
 *
 */
object PasswordErrorsChecker {

    /**
     * Constants.
     */
    private const val PASSWORD_ERRORS_SEPARATOR = "\n"
    private const val REGEX_ONE_UPPER_CHAR = ".*[A-Z].*"
    private const val REGEX_ONE_LOWER_CHAR = ".*[a-z].*"
    private const val SPECIAL_CHARS = "@#$%^&;+="
    private const val REGEX_ONE_SPECIAL_CHAR = ".*[$SPECIAL_CHARS].*"

    /**
     * Check password for errors.
     */
    fun checkPasswordErrors(passwordText: String, fragment: Fragment): String {

        // Do all checks.
        var result = ""
        if (passwordText.length < Constants.MIN_PASSWORD_LENGTH) {                          // Minimum # chars.
            result += fragment.getString(
                R.string.auth_activity_password_warning_min_chars,
                Constants.MIN_PASSWORD_LENGTH
            )
        }

        val maxPasswordLength =
            fragment.resources.getInteger(R.integer.count_loginFragment_passwordMaxLength)  // Minimum # chars.
        if (passwordText.length > maxPasswordLength) {
            result =
                addErrorsDescriptionSeparator(result) + fragment.getString(
                    R.string.auth_activity_password_warning_max_chars,
                    maxPasswordLength
                )
        }
        if (!passwordText.matches(REGEX_ONE_UPPER_CHAR.toRegex())) {                        // Minimum 1 UpperCase char.
            result =
                addErrorsDescriptionSeparator(result) + fragment.getString(R.string.auth_activity_password_warning_one_upper_char)
        }
        if (!passwordText.matches(REGEX_ONE_LOWER_CHAR.toRegex())) {                        // Minimum 1 LowerCase char.
            result =
                addErrorsDescriptionSeparator(result) + fragment.getString(R.string.auth_activity_password_warning_one_lower_char)
        }
        if (!passwordText.matches(REGEX_ONE_SPECIAL_CHAR.toRegex())) {                      // Minimum 1 special char.
            result = addErrorsDescriptionSeparator(result) + fragment.getString(
                R.string.auth_activity_password_warning_one_special_char,
                SPECIAL_CHARS
            )
        }
        return result
    }

    /**
     * Add separator between two errors.
     */
    private fun addErrorsDescriptionSeparator(result: String): String =
        if (result != "") "$result$PASSWORD_ERRORS_SEPARATOR" else result
}