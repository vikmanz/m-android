package com.vikmanz.shpppro.ui

import android.content.Intent
import android.os.Bundle
import com.vikmanz.shpppro.R
import com.vikmanz.shpppro.databinding.ActivityMainBinding
import com.vikmanz.shpppro.constants.Constants.INTENT_EMAIL_ID
import com.vikmanz.shpppro.constants.Constants.INTENT_LANG_ID
import com.vikmanz.shpppro.data.DataStoreManager
import com.vikmanz.shpppro.utilits.BaseActivity
import com.vikmanz.shpppro.utilits.firstCharToUpperCase
import kotlinx.coroutines.*
import java.util.*

/**
 * Class represents user main profile screen activity.
 */
class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    // Data Store and Coroutine Scope variables.
    private lateinit var loginData: DataStoreManager
    private val coroutineScope: CoroutineScope = CoroutineScope(Job())


    // Save state of language. True - En, False - Ua.
    private var isUkrainian = false

    /**
     * Main function, which used when activity was create.
     */
    override fun onCreate(savedInstanceState: Bundle?) {

        // Init activity.
        super.onCreate(savedInstanceState)

        // Get and set locale.
        isUkrainian = intent.getBooleanExtra(INTENT_LANG_ID, true)
        setLocale()

        // Create Data Store.
        loginData = DataStoreManager(this)

        // Parse email, set Name Surname text and img of avatar.
        setUserInformation()
        setAvatar()
    }


    override fun setListeners() {
        super.setListeners()
        binding.textviewMainLogoutButton.setOnClickListener { logout() }
    }

    /**
     * Get full email, parse it and set name/surname of user.
     */
    private fun setUserInformation() {
        val emailToParse = intent.getStringExtra(INTENT_EMAIL_ID).toString()
        with(binding){
            textviewMainPersonName.text = if (emailToParse.isEmpty()) "" else parseEmail(emailToParse)
            textviewMainPersonCareer.text = getString(R.string.main_activity_person_career_hardcoded)
            textviewMainPersonAddress.text = getString(R.string.main_activity_person_address_hardcoded)
        }
    }

    /**
     * Parse email to Name Surname.
     *
     * @param fullEmail string with full user email.
     * @return parsed Name Surname as String.
     */
    private fun parseEmail(fullEmail: String): String {

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

    /**
     * Set avatar image.
     */
    private fun setAvatar() = binding.imageviewMainAvatarImage.setImageResource(R.drawable.sample_avatar)

    /**
     * Logout with clear information about user from Data Store.
     */
    private fun logout() {
        coroutineScope.launch(Dispatchers.IO) { loginData.clearUser() }
        finishActivity()
    }

    /**
     * Finish that activity and start SignIn/SignUp activity.
     */
    private fun finishActivity() {
        val intentObject = Intent(this, AuthActivity::class.java)
        intentObject.putExtra(INTENT_LANG_ID, isUkrainian)
        finish()
        startActivity(intentObject)
        overridePendingTransition(R.anim.zoom_out_inner, R.anim.zoom_out_outter)
    }

    /**
     * Change locale. It change from EN to UA or from UA to EN.
     */
    private fun setLocale() {
        val config = resources.configuration
        val lang =
            if (isUkrainian) LANG_UA else LANG_EN
        val locale = Locale(lang)
        Locale.setDefault(locale)
        config.setLocale(locale)
        createConfigurationContext(config)
        @Suppress("DEPRECATION")
        resources.updateConfiguration(config, resources.displayMetrics)
    }

    /**
     * Save Instance State.
     */
    override fun onSaveInstanceState(outState: Bundle) {
        outState.putBoolean(LANGUAGE_STATE_KEY, isUkrainian)
        super.onSaveInstanceState(outState)
    }

    /**
     * Load Instance State.
     */
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        isUkrainian = savedInstanceState.getBoolean(LANGUAGE_STATE_KEY)
        setLocale()
    }

    /**
     * Constants.
     */
    companion object {
        private const val EMAIL_DOMAIN_SEPARATOR = '@'
        private const val EMAIL_NAME_SEPARATOR = '.'
        private const val EMAIL_NAME_JOIN_SPASE = " "
        private const val REGEX_FROM_A_TO_Z = "[A-Z]"
        private const val LANG_EN = "en"
        private const val LANG_UA = "uk"
        private const val LANGUAGE_STATE_KEY = "LANG_ID_KEY_MAIN_ACTIVITY"
    }

}

