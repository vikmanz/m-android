package com.vikmanz.shpppro.ui

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.asLiveData
import com.vikmanz.shpppro.R
import com.vikmanz.shpppro.utilits.Constants.INTENT_EMAIL_ID
import com.vikmanz.shpppro.utilits.Constants.INTENT_LANG_ID
import com.vikmanz.shpppro.utilits.Constants.LOGIN_VIEW_FIRST
import com.vikmanz.shpppro.utilits.Constants.MIN_PASSWORD_LENGTH
import com.vikmanz.shpppro.utilits.Constants.VIEW_HELP_BUTTONS_ON_CREATE
import com.vikmanz.shpppro.data.DataStoreManager
import com.vikmanz.shpppro.databinding.ActivityAuthBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*

/**
 * Class represents SignIn or SignUp screen activity .
 */
class AuthActivity : AppCompatActivity() {

    // Binding, Data Store and Coroutine Scope variables.
    private lateinit var binding: ActivityAuthBinding
    private lateinit var loginData: DataStoreManager
    private val coroutineScope: CoroutineScope = CoroutineScope(Job())

    // Save state of screen layout. True - Login screen, False - Register screen.
    private var isLoginScreen = false

    // Save state of language. True - En, False - Ua.
    private var isUkrainian = false

    // Save state of helper buttons. True - visible, False - gone.
    private var helperButtonsVisible = false

    /**
     * Main function, which used when activity was create.
     */
    override fun onCreate(savedInstanceState: Bundle?) {

        // init.
        super.onCreate(savedInstanceState)

        // Load saved data and do autologin if need.
        loginData = DataStoreManager(this)
        checkAutoLogin()

        // binding, add content and set Login or Register screen first.
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (LOGIN_VIEW_FIRST) swapSignInSignUpScreens()

        // init main functions.
        setLoginPasswordFocusListeners()                 // Listeners to fields and buttons.
        initSubmitRegisterAndSwapInUpButtonsListeners()  // Change Register to Login and another.

        // init additional features.
        initHelpTesterButtons()     // help test buttons
        backgroundFocusHandler()    // de-focus fields when click on bg
    }

    /**
     * Check if user already save login-password, and do autologin if it's need.
     */
    private fun checkAutoLogin() {
        var email = ""
        loginData.userNameFlow.asLiveData().observe(this) { email = it }
        loginData.userLanguageFlow.asLiveData().observe(this) { isUkrainian = it }
        loginData.userLoginStatusFlow.asLiveData().observe(this) {
            if (it) startMainActivity(email)
        }
    }

    /**
     * Start main activity.
     *
     * @param email User email as String.
     */
    private fun startMainActivity(email: String) {
        val intentObject = Intent(this, MainActivity::class.java)
        intentObject.putExtra(INTENT_EMAIL_ID, email)
        intentObject.putExtra(INTENT_LANG_ID, isUkrainian)
        startActivity(intentObject)
        overridePendingTransition(R.anim.zoom_in_inner, R.anim.zoom_in_outter)
        finish()
    }

    /**
     * Swap SignIn ans SignUp screens via changing text and visibility view on layout.
     */
    private fun swapSignInSignUpScreens() {
        isLoginScreen = !isLoginScreen  // Change variable-state of screen layout.
        with(binding) {
            if (isLoginScreen) {
                textviewAuthHelloText.text = getString(R.string.auth_activity_hello_text_login_screen)
                textviewAuthHelloSubtext.text = getString(R.string.auth_activity_hello_subtext_login_screen)
                textinputlayoutAuthPassword.isCounterEnabled = true
                textviewAuthForgotPassword.visibility = View.VISIBLE
                buttonAuthRegisterByGoogle.visibility = View.GONE
                textviewAuthTextBetweenGoogleAndRegister.visibility = View.GONE
                buttonAuthRegisterByEmail.text = getString(R.string.auth_activity_register_button_login_screen)
                textviewAuthWarningAboutTerms.visibility = View.GONE
                textviewAuthAlreadyHaveAccountMessage.text = getString(R.string.auth_activity_already_have_account_message_login_screen)
                textviewAuthSwitchScreenToLoginButton.text = getString(R.string.auth_activity_sign_in_button_login_screen)
            } else {
                textviewAuthHelloText.text = getString(R.string.auth_layout_hello_text)
                textviewAuthHelloSubtext.text = getString(R.string.auth_layout_hello_subtext)
                textinputlayoutAuthPassword.isCounterEnabled = true
                textviewAuthForgotPassword.visibility = View.INVISIBLE
                buttonAuthRegisterByGoogle.visibility = View.VISIBLE
                textviewAuthTextBetweenGoogleAndRegister.visibility = View.VISIBLE
                buttonAuthRegisterByEmail.text = getString(R.string.auth_layout_register_button)
                textviewAuthWarningAboutTerms.visibility = View.VISIBLE
                textviewAuthAlreadyHaveAccountMessage.text = getString(R.string.auth_layout_already_have_account_message)
                textviewAuthSwitchScreenToLoginButton.text = getString(R.string.auth_layout_sign_in_button)
            }
        }
    }

    /**
     * Set onClickListeners for email and password text input fields.
     */
    private fun setLoginPasswordFocusListeners() {
        with(binding) {
            textinputAuthEmail.setOnFocusChangeListener { _, focused ->
                if (!focused) textinputlayoutAuthEmail.helperText = validEmail()
            }
            textinputAuthPassword.setOnFocusChangeListener { _, focused ->
                if (!focused) textinputlayoutAuthPassword.helperText = validPassword()
            }
        }
    }

    /**
     * Validate input email for errors.
     *
     * @return Invalid message if email contains errors, or null if not.
     */
    private fun validEmail(): String? {
        // Get email text
        val emailText = binding.textinputAuthEmail.text.toString()

        // Do check for standard Patterns.EMAIL_ADDRESS regex.
        if (!Patterns.EMAIL_ADDRESS.matcher(emailText).matches()) {
            return getString(R.string.auth_activity_warning_message_invalid_email)
        }

        // If pass check, return null.
        return null
    }

    /**
     * Validate input password for errors.
     *
     * @return Invalid message if password contains errors, or null if not.
     */
    private fun validPassword(): String? {

        // Get password text
        val passwordText = binding.textinputAuthPassword.text.toString()

        // Do all checks.
        if (passwordText.length < MIN_PASSWORD_LENGTH) {               // Minimum 8 chars.
            return getString(R.string.auth_activity_password_warning_min8chars, MIN_PASSWORD_LENGTH)
        }
        if (!passwordText.matches(REGEX_ONE_UPPER_CHAR.toRegex())) {   // Minimum 1 UpperCase char.
            return getString(R.string.auth_activity_password_warning_one_upper_char)
        }
        if (!passwordText.matches(REGEX_ONE_LOWER_CHAR.toRegex())) {   // Minimum 1 LowerCase char.
            return getString(R.string.auth_activity_password_warning_one_lower_char)
        }
        Log.d("myLog", REGEX_ONE_SPECIAL_CHAR)
        Log.d("myLog", "${passwordText.matches(REGEX_ONE_SPECIAL_CHAR.toRegex())}")
        if (!passwordText.matches(REGEX_ONE_SPECIAL_CHAR.toRegex())) { // Minimum 1 special char.
            return getString(R.string.auth_activity_password_warning_one_special_char, SPECIAL_CHARS)
        }

        // If pass all checks, return null.
        return null
    }

    /**
     * Init OnClickListeners for button Login/Register and SignIn/SignUp text.
     */
    private fun initSubmitRegisterAndSwapInUpButtonsListeners() {
        with(binding) {
            buttonAuthRegisterByEmail.setOnClickListener { submitForm() }
            textviewAuthSwitchScreenToLoginButton.setOnClickListener { swapSignInSignUpScreens() }
        }
    }

    /**
     *  Submit Login/Register form and start MainActivity or show error messages.
     */
    private fun submitForm() {
        with(binding) {

            // Validate email and password.
            textinputlayoutAuthEmail.helperText = validEmail()
            textinputlayoutAuthPassword.helperText = validPassword()
            val isEmailCorrect = textinputlayoutAuthEmail.helperText == null
            val isPasswordCorrect = textinputlayoutAuthPassword.helperText == null

            // If valid, start MainActivity.
            if (isEmailCorrect && isPasswordCorrect) {
                // If check Remember, save data to Data Store.
                if (checkboxAuthRememberMe.isChecked) saveUserData()
                val emailText = textinputAuthEmail.text.toString()
                startMainActivity(emailText)
            }
            // If have error - show error message.
            else {
                showInvalidFormMessage(isEmailCorrect, isPasswordCorrect)
            }
        }
    }

    /**
     *  Save user data from text input fields and language key from class variable to Data Store.
     */
    private fun saveUserData() {
        val email: String
        val password: String
        val isAutologin: Boolean
        val isEnglish = this.isUkrainian

        with(binding) {
            email = textinputAuthEmail.text.toString()
            password = textinputAuthPassword.text.toString()
            isAutologin = checkboxAuthRememberMe.isChecked
        }

        coroutineScope.launch(Dispatchers.IO) {
            loginData.saveUserSata(email, password, isAutologin, isEnglish)
        }
    }

    /**
     *  Get error status of email and password, and show error message.
     */
    private fun showInvalidFormMessage(isEmailCorrect: Boolean, isPasswordCorrect: Boolean) {

        // Create error message.
        var message = ""
        with(binding) {
            message += if (!isEmailCorrect)
                "\n\n${getString(R.string.auth_activity_warning_message_email_title)} ${textinputlayoutAuthEmail.helperText}"
            else ""
            message += if (!isPasswordCorrect)
                "\n\n${getString(R.string.auth_activity_warning_message_password_title)} ${textinputlayoutAuthPassword.helperText}"
            else ""
        }

        // Show AlertDialog with error message.
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.auth_activity_warning_message_invalid_register_form))
            .setMessage(message)
            .setPositiveButton(getString(R.string.auth_activity_warning_message_ok_button_text)) { _, _ ->
                with(binding) {
                    // Set focus to first field with error.
                    if (isEmailCorrect) textinputAuthEmail.requestFocus()
                    else textinputAuthPassword.requestFocus()
                }
            }
            .show()
    }

    /**
     *  Init helper buttons for tests.
     *  It will be removed from release version.
     */
    private fun initHelpTesterButtons() {

        // View help buttons when create activity if it need.
        if (VIEW_HELP_BUTTONS_ON_CREATE) viewOrHideHelpTesterButtons()

        // Set onClickListeners().
        with(binding) {
            // Set onClickListener to message "Do you have account" to see help buttons.
            textviewAuthAlreadyHaveAccountMessage.setOnClickListener { viewOrHideHelpTesterButtons() }

            // Fill fields button listener.
            buttonAuthFillLoginAndPass.setOnClickListener {
                textinputAuthEmail.setText(TEST_LOGIN)
                textinputAuthPassword.setText(TEST_PASSWORD)
                textinputlayoutAuthEmail.helperText = null
                textinputlayoutAuthPassword.helperText = null
            }

            // Clear fields button listener.
            buttonAuthClearLoginPassFields.setOnClickListener {
                textinputAuthEmail.setText("")
                textinputAuthPassword.setText("")
                checkboxAuthRememberMe.isChecked = false
            }

            // Language change button listener.
            buttonAuthChangeLanguage.setOnClickListener {
                isUkrainian = !isUkrainian
                setLocale()
                super.recreate()    // RECREATE ACTIVITY
            }
        }
    }

    /**
     *  Show/Hide help buttons.
     */
    private fun viewOrHideHelpTesterButtons() {
        helperButtonsVisible = !helperButtonsVisible
        binding.flowAuthDebugButtons.visibility = if (helperButtonsVisible) View.VISIBLE else View.GONE
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
     *  De-focus views, when user do click on background.
     */
    private fun backgroundFocusHandler() = with(binding) {
        root.setOnClickListener { textinputAuthEmail.clearFocus() }
    }

    /**
     * Save Instance State.
     */
    override fun onSaveInstanceState(outState: Bundle) {
        with(binding) {
            outState.putString(EMAIL_FIELD_STATE_KEY, textinputAuthEmail.text.toString())
            outState.putString(PASSWORD_FIELD_STATE_KEY, textinputAuthPassword.text.toString())
            outState.putInt(PASSWORD_VIEW_STATE_KEY, textinputlayoutAuthPassword.endIconMode)
            outState.putBoolean(CHECKBOX_STATE_STATE_KEY, checkboxAuthRememberMe.isChecked)
        }
        outState.putBoolean(LANGUAGE_STATE_KEY, isUkrainian)
        outState.putBoolean(HELP_BUTTONS_STATE_KEY, helperButtonsVisible)
        super.onSaveInstanceState(outState)
    }

    /**
     * Load Instance State.
     */
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        isUkrainian = savedInstanceState.getBoolean(LANGUAGE_STATE_KEY)
        helperButtonsVisible = savedInstanceState.getBoolean(HELP_BUTTONS_STATE_KEY)
        with(binding) {
            textinputAuthEmail.setText(savedInstanceState.getString(EMAIL_FIELD_STATE_KEY))
            textinputAuthPassword.setText(savedInstanceState.getString(PASSWORD_FIELD_STATE_KEY))
            textinputlayoutAuthPassword.endIconMode = savedInstanceState.getInt(PASSWORD_VIEW_STATE_KEY)
            checkboxAuthRememberMe.isChecked = savedInstanceState.getBoolean(CHECKBOX_STATE_STATE_KEY)
            flowAuthDebugButtons.visibility = if (helperButtonsVisible) View.VISIBLE else View.GONE
        }
        setLocale()
    }

    companion object {
        // Regex's
        private const val REGEX_ONE_UPPER_CHAR = ".*[A-Z].*"
        private const val REGEX_ONE_LOWER_CHAR = ".*[a-z].*"
        private const val SPECIAL_CHARS = "@#$%^&amp;+="
        private const val REGEX_ONE_SPECIAL_CHAR = ".*[@#\$%^&amp;+=].*"        //TODO ERROR!
        private const val LANG_EN = "en"
        private const val LANG_UA = "uk"
        private const val TEST_LOGIN = "viktor.manza@gmail.com"
        private const val TEST_PASSWORD = "passwordE3"

        // Save/Load State Keys. Don't need to change.
        private const val EMAIL_FIELD_STATE_KEY = "EMAIL_KEY_AUTH_ACTIVITY"
        private const val PASSWORD_FIELD_STATE_KEY = "PASSWORD_KEY_AUTH_ACTIVITY"
        private const val PASSWORD_VIEW_STATE_KEY = "PASSWORD_VIEW_KEY_AUTH_ACTIVITY"
        private const val CHECKBOX_STATE_STATE_KEY = "CHECKBOX_KEY_AUTH_ACTIVITY"
        private const val LANGUAGE_STATE_KEY = "LAND_ID_KEY_AUTH_ACTIVITY"
        private const val HELP_BUTTONS_STATE_KEY = "HELP_BUTTONS_KEY_AUTH_ACTIVITY"
    }

}
