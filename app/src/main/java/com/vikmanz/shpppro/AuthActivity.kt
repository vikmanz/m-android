package com.vikmanz.shpppro

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.asLiveData
import com.vikmanz.shpppro.constants.Constants.CHECKBOX_STATE_STATE_KEY
import com.vikmanz.shpppro.constants.Constants.EMAIL_FIELD_STATE_KEY
import com.vikmanz.shpppro.constants.Constants.HELP_BUTTONS_STATE_KEY
import com.vikmanz.shpppro.constants.Constants.INTENT_EMAIL_ID
import com.vikmanz.shpppro.constants.Constants.INTENT_LANG_ID
import com.vikmanz.shpppro.constants.Constants.LANGUAGE_STATE_KEY
import com.vikmanz.shpppro.constants.Constants.LOGIN_VIEW_FIRST
import com.vikmanz.shpppro.constants.Constants.MIN_PASSWORD_LENGTH
import com.vikmanz.shpppro.constants.Constants.PASSWORD_FIELD_STATE_KEY
import com.vikmanz.shpppro.constants.Constants.PASSWORD_VIEW_STATE_KEY
import com.vikmanz.shpppro.constants.Constants.VIEW_HELP_BUTTONS_ON_CREATE
import com.vikmanz.shpppro.dataSave.LoginDataStoreManager
import com.vikmanz.shpppro.databinding.ActivityAuthBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*

/**
 * Class represents SignIn or SignUp screen activity.
 */
class AuthActivity : AppCompatActivity() {

    // Binding, Data Store and Coroutine Scope variables.
    private lateinit var binding: ActivityAuthBinding
    private lateinit var loginData: LoginDataStoreManager
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
        loginData = LoginDataStoreManager(this)
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
                tvHelloText.text = getString(R.string.helloText_signin)
                tvHelloSubText.text = getString(R.string.helloSubText_signin)
                tiLayoutPassword.isCounterEnabled = true
                forgotPassword.visibility = View.VISIBLE
                bRegisterByGoogle.visibility = View.GONE
                googleOrRegister.visibility = View.GONE
                bRegisterByEmailPassword.text = getString(R.string.registerButton_signin)
                warningAboutTerms.visibility = View.GONE
                alreadyHaveAccMessage.text = getString(R.string.already_have_an_account_signin)
                alreadyHaveAccLink.text = getString(R.string.sign_in_signin)
            } else {
                tvHelloText.text = getString(R.string.helloText)
                tvHelloSubText.text = getString(R.string.helloSubText)
                tiLayoutPassword.isCounterEnabled = true
                forgotPassword.visibility = View.INVISIBLE
                bRegisterByGoogle.visibility = View.VISIBLE
                googleOrRegister.visibility = View.VISIBLE
                bRegisterByEmailPassword.text = getString(R.string.registerButton)
                warningAboutTerms.visibility = View.VISIBLE
                alreadyHaveAccMessage.text = getString(R.string.already_have_an_account)
                alreadyHaveAccLink.text = getString(R.string.sign_in)
            }
        }
    }

    /**
     * Set onClickListeners for email and password text input fields.
     */
    private fun setLoginPasswordFocusListeners() {
        with(binding) {
            tiTextEmail.setOnFocusChangeListener { _, focused ->
                if (!focused) tiLayoutEmail.helperText = validEmail()
            }
            tiTextPassword.setOnFocusChangeListener { _, focused ->
                if (!focused) tiLayoutPassword.helperText = validPassword()
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
        val emailText = binding.tiTextEmail.text.toString()

        // Do check for standard Patterns.EMAIL_ADDRESS regex.
        if (!Patterns.EMAIL_ADDRESS.matcher(emailText).matches()) {
            return getString(R.string.warningMessage_InvalidEmailAdress)
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
        val passwordText = binding.tiTextPassword.text.toString()

        // Get Regex
        val regexOneUpperCaseChar = getString(R.string.regex_pass_OneUpperChar).toRegex()
        val regexOneLowerCaseChar = getString(R.string.regex_pass_OneLowerChar).toRegex()
        val regexOneSpecialChar = getString(R.string.regex_pass_OneSpecialChar).toRegex()

        // Do all checks.
        if (passwordText.length < MIN_PASSWORD_LENGTH) {            // Minimum 8 chars.
            return getString(R.string.passCheckWarning_min8chars)
        }
        if (!passwordText.matches(regexOneUpperCaseChar)) {         // Minimum 1 UpperCase char.
            return getString(R.string.passCheckWarning_OneUpperChar)
        }
        if (!passwordText.matches(regexOneLowerCaseChar)) {         // Minimum 1 LowerCase char.
            return getString(R.string.passCheckWarning_OneLowerChar)
        }
        if (!passwordText.matches(regexOneSpecialChar)) {           // Minimum 1 special char.
            return getString(R.string.passCheckWarning_OneSpecialChar)
        }

        // If pass all checks, return null.
        return null
    }

    /**
     * Init OnClickListeners for button Login/Register and SignIn/SignUp text.
     */
    private fun initSubmitRegisterAndSwapInUpButtonsListeners() {
        with(binding) {
            bRegisterByEmailPassword.setOnClickListener { submitForm() }
            alreadyHaveAccLink.setOnClickListener { swapSignInSignUpScreens() }
        }
    }

    /**
     *  Submit Login/Register form and start MainActivity or show error messages.
     */
    private fun submitForm() {
        with(binding) {

            // Validate email and password.
            tiLayoutEmail.helperText = validEmail()
            tiLayoutPassword.helperText = validPassword()
            val isEmailCorrect = tiLayoutEmail.helperText == null
            val isPasswordCorrect = tiLayoutPassword.helperText == null

            // If valid, start MainActivity.
            if (isEmailCorrect && isPasswordCorrect) {
                // If check Remember, save data to Data Store.
                if (checkBox.isChecked) saveUserData()
                val emailText = tiTextEmail.text.toString()
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
            email = tiTextEmail.text.toString()
            password = tiTextPassword.text.toString()
            isAutologin = checkBox.isChecked
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
            message += if (isEmailCorrect)
                "\n\n${getString(R.string.warning_emailTitle)} ${tiLayoutEmail.helperText}"
            else ""
            message += if (isPasswordCorrect)
                "\n\n${getString(R.string.warning_passwordTitle)} ${tiLayoutPassword.helperText}"
            else ""
        }

        // Show AlertDialog with error message.
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.warningMessage_InvalidRegisterForm))
            .setMessage(message)
            .setPositiveButton(getString(R.string.warningMessage_OkButtonOnAllert)) { _, _ ->
                with(binding) {
                    // Set focus to first field with error.
                    if (isEmailCorrect) tiTextEmail.requestFocus()
                    else tiTextPassword.requestFocus()
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
            alreadyHaveAccMessage.setOnClickListener { viewOrHideHelpTesterButtons() }

            // Fill fields button listener.
            buttonFillLoginPassword.setOnClickListener {
                tiTextEmail.setText(getString(R.string.my_main_email))
                tiTextPassword.setText(getString(R.string.my_main_password))
                tiLayoutEmail.helperText = null
                tiLayoutPassword.helperText = null
            }

            // Clear fields button listener.
            buttonClearUserPassword.setOnClickListener {
                tiTextEmail.setText("")
                tiTextPassword.setText("")
                checkBox.isChecked = false
            }

            // Language change button listener.
            buttonChangeLanguage.setOnClickListener {
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
        binding.flowTestButtons.visibility = if (helperButtonsVisible) View.VISIBLE else View.GONE
    }

    /**
     * Change locale. It change from EN to UA or from UA to EN.
     */
    private fun setLocale() {
        val config = resources.configuration
        val lang =
            if (isUkrainian) getString(R.string.language_ua) else getString(R.string.language_en)
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
        root.setOnClickListener { tiTextEmail.clearFocus() }
    }

    /**
     * Save Instance State.
     */
    override fun onSaveInstanceState(outState: Bundle) {
        with(binding) {
            outState.putString(EMAIL_FIELD_STATE_KEY, tiTextEmail.text.toString())
            outState.putString(PASSWORD_FIELD_STATE_KEY, tiTextPassword.text.toString())
            outState.putInt(PASSWORD_VIEW_STATE_KEY, tiLayoutPassword.endIconMode)
            outState.putBoolean(CHECKBOX_STATE_STATE_KEY, checkBox.isChecked)
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
            tiTextEmail.setText(savedInstanceState.getString(EMAIL_FIELD_STATE_KEY))
            tiTextPassword.setText(savedInstanceState.getString(PASSWORD_FIELD_STATE_KEY))
            tiLayoutPassword.endIconMode = savedInstanceState.getInt(PASSWORD_VIEW_STATE_KEY)
            checkBox.isChecked = savedInstanceState.getBoolean(CHECKBOX_STATE_STATE_KEY)
            flowTestButtons.visibility = if (helperButtonsVisible) View.VISIBLE else View.GONE
        }
        setLocale()
    }

}
