package com.vikmanz.shpppro

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.vikmanz.shpppro.databinding.ActivityMainBinding
import com.vikmanz.shpppro.constants.Constants.INTENT_EMAIL_ID
import com.vikmanz.shpppro.constants.Constants.INTENT_LANG_ID
import com.vikmanz.shpppro.constants.Constants.LANGUAGE_STATE_KEY_TWO
import com.vikmanz.shpppro.dataSave.LoginDataStoreManager
import kotlinx.coroutines.*
import java.util.*

/**
 * Class represents user main profile activity.
 */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var loginData: LoginDataStoreManager
    private val coroutineScope: CoroutineScope = CoroutineScope(Job())

    private var isEnglish = true

    override fun onCreate(savedInstanceState: Bundle?) {

        // Init activity.
        super.onCreate(savedInstanceState)

        // Get and set locale.
        isEnglish = intent.getBooleanExtra(INTENT_LANG_ID, true)
        setLocale()

        // Others init operations.
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Create Data Store.
        loginData = LoginDataStoreManager(this)

        // Parse email, set Name Surname text and img of avatar.
        val emailToParse = intent.getStringExtra(INTENT_EMAIL_ID).toString()
        binding.tvPersonName.text = if (emailToParse.isEmpty()) "" else parseEmail(emailToParse)
        binding.ivPerson.setImageResource(R.drawable.sample_avatar)

        // Set onClick listener to Logout button.
        binding.tvLogout.setOnClickListener { logout() }
    }

    /**
     * Parse email to Name Surname.
     *
     * @param fullEmail string with full user email.
     * @return parsed Name Surname as String.
     */
    private fun parseEmail(fullEmail: String): String {

        val personName: String
        val firstPartEmail = fullEmail.substring(0, fullEmail.indexOf('@'))

        // if "nameSurname" variant:
        if (firstPartEmail.indexOf('.') == -1) {
            val regex = getString(R.string.regex_fromBigAtoBigZ_char).toRegex()
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
                .split('.')
                .joinToString(" ", transform = String::firstCharToUpperCase)
        }

        return personName
    }

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
        intentObject.putExtra(INTENT_LANG_ID, isEnglish)
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
            if (isEnglish) getString(R.string.language_en) else getString(R.string.language_ua)
        val locale = Locale(lang)
        Locale.setDefault(locale)
        config.setLocale(locale)
        createConfigurationContext(config)
        @Suppress("DEPRECATION")
        resources.updateConfiguration(config, resources.displayMetrics)
    }

    /**
     * Save state.
     */
    override fun onSaveInstanceState(outState: Bundle) {
        outState.putBoolean(LANGUAGE_STATE_KEY_TWO, isEnglish)
        super.onSaveInstanceState(outState)
    }

    /**
     * Load state.
     */
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        isEnglish = savedInstanceState.getBoolean(LANGUAGE_STATE_KEY_TWO)
        setLocale()
    }

}

/**
 * Extra function of String class, for replace the first char of String to Upper case.
 */
fun String.firstCharToUpperCase() = replaceFirstChar {
    if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
}