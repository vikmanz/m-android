package com.vikmanz.shpppro.data.utils

import com.vikmanz.shpppro.presentation.utils.extensions.firstCharToUpperCase
import java.util.*

/**
 * Class which get full email and parse email to "Name Surname".
 *
 */
object EmailParser {

    /**
     * Constants.
     */
    private const val EMAIL_DOMAIN_SEPARATOR = '@'
    private const val EMAIL_NAME_SEPARATOR = '.'
    private const val EMAIL_NAME_JOIN_SPASE = " "
    private const val REGEX_FROM_A_TO_Z = "[A-Z]"

    /**
     * Parse email to Name Surname.
     *
     * @param fullEmail string with full user email.
     * @return parsed Name Surname as String.
     */
    fun getParsedNameSurname(fullEmail: String): String {

        val personName: String
        val firstPartEmail = fullEmail.substring(0, fullEmail.indexOf(EMAIL_DOMAIN_SEPARATOR))

        // if "nameSurname" variant:
        if (firstPartEmail.indexOf(EMAIL_NAME_SEPARATOR) == -1) {
            val regex = REGEX_FROM_A_TO_Z.toRegex()
            val match: MatchResult? = regex.find(firstPartEmail.substring(1))
            personName = if (match == null) {
                firstPartEmail
            } else {
                val surnameStartIndex = match.range.first + 1
                "${
                    firstPartEmail
                        .substring(0, surnameStartIndex)
                        .replaceFirstChar {
                            if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
                        }
                } ${firstPartEmail.substring(surnameStartIndex)}"
            }
        }

        // else if "name.surname" variant:
        else {
            personName = firstPartEmail
                .split(EMAIL_NAME_SEPARATOR)
                .joinToString(EMAIL_NAME_JOIN_SPASE, transform = String::firstCharToUpperCase)
        }

        return personName
    }

}