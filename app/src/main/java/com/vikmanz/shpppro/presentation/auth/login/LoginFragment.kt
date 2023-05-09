package com.vikmanz.shpppro.presentation.auth.login

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Patterns
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import com.vikmanz.shpppro.R
import com.vikmanz.shpppro.data.DataStoreManager
import com.vikmanz.shpppro.presentation.base.BaseArgument
import com.vikmanz.shpppro.presentation.base.BaseFragment
import com.vikmanz.shpppro.presentation.main.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import com.vikmanz.shpppro.constants.Constants.INTENT_EMAIL_ID
import com.vikmanz.shpppro.constants.Constants.LOGIN_VIEW_FIRST
import com.vikmanz.shpppro.constants.Constants.MIN_PASSWORD_LENGTH
import com.vikmanz.shpppro.constants.Constants.VIEW_HELP_BUTTONS_ON_CREATE
import com.vikmanz.shpppro.databinding.FragmentLoginBinding
import com.vikmanz.shpppro.presentation.utils.extensions.clearError
import com.vikmanz.shpppro.presentation.utils.extensions.setGone
import com.vikmanz.shpppro.presentation.utils.extensions.setInvisible
import com.vikmanz.shpppro.presentation.utils.extensions.setMultipleGone
import com.vikmanz.shpppro.presentation.utils.extensions.setMultipleVisible
import com.vikmanz.shpppro.presentation.utils.extensions.setVisible
import com.vikmanz.shpppro.presentation.utils.screenAuthViewModel

/**
 * Constants.
 */
private const val PASSWORD_ERRORS_SEPARATOR = "\n"
private const val REGEX_ONE_UPPER_CHAR = ".*[A-Z].*"
private const val REGEX_ONE_LOWER_CHAR = ".*[a-z].*"
private const val SPECIAL_CHARS = "@#$%^&;+="
private const val REGEX_ONE_SPECIAL_CHAR = ".*[$SPECIAL_CHARS].*"
private const val TEST_LOGIN = "viktor.manza@gmail.com"
private const val TEST_PASSWORD = "passwordE3@a"

// Save/Load State Keys. Don't need to change.
private const val EMAIL_FIELD_STATE_KEY = "EMAIL_KEY_AUTH_ACTIVITY"
private const val PASSWORD_FIELD_STATE_KEY = "PASSWORD_KEY_AUTH_ACTIVITY"
private const val PASSWORD_VIEW_STATE_KEY = "PASSWORD_VIEW_KEY_AUTH_ACTIVITY"
private const val CHECKBOX_STATE_STATE_KEY = "CHECKBOX_KEY_AUTH_ACTIVITY"
private const val HELP_BUTTONS_STATE_KEY = "HELP_BUTTONS_KEY_AUTH_ACTIVITY"

class LoginFragment :
    BaseFragment<FragmentLoginBinding, LoginViewModel>(FragmentLoginBinding::inflate) {

    class CustomArgument() : BaseArgument

    /**
     * Create ViewModel for this activity.
     */
    override val viewModel by screenAuthViewModel()

    private lateinit var uiObserver: Observer<Boolean>
    private lateinit var helpersObserver: Observer<Boolean>

    private lateinit var dataStore: DataStoreManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataStore = DataStoreManager(requireActivity())
    }

    override fun setStartUi() {}

    override fun setObservers() {
        observeUI()
        observeHelpers()
    }

    override fun setListeners() {
        with(binding) {
            buttonAuthRegisterByEmail.setOnClickListener { submitForm() }
            textviewAuthSwitchScreenToLoginButton.setOnClickListener { viewModel.swapLoginAndRegister() }
        }
        initHelpTesterButtons()
        setLoginPasswordFocusListeners()                 // Listeners to fields and buttons.
        backgroundFocusHandler()    // de-focus fields when click on bg
    }

    /**
     * Observe and swap SignIn ans SignUp screens via changing text and visibility view on layout.
     */
    private fun observeUI() {
        uiObserver = Observer {
            with(binding) {
                if (it) {
                    textviewAuthHelloText.text =
                        getString(R.string.auth_activity_hello_text_login_screen)
                    textviewAuthHelloSubtext.text =
                        getString(R.string.auth_activity_hello_subtext_login_screen)
                    buttonAuthRegisterByEmail.text =
                        getString(R.string.auth_activity_register_button_login_screen)
                    textviewAuthAlreadyHaveAccountMessage.text =
                        getString(R.string.auth_activity_already_have_account_message_login_screen)
                    textviewAuthSwitchScreenToLoginButton.text =
                        getString(R.string.auth_activity_sign_in_button_login_screen)
                    textviewAuthForgotPassword.setVisible()
                    setMultipleGone(
                        buttonAuthRegisterByGoogle,
                        textviewAuthTextBetweenGoogleAndRegister,
                        textviewAuthWarningAboutTerms
                    )
                } else {
                    textviewAuthHelloText.text = getString(R.string.auth_layout_hello_text)
                    textviewAuthHelloSubtext.text =
                        getString(R.string.auth_layout_hello_subtext)
                    buttonAuthRegisterByEmail.text =
                        getString(R.string.auth_layout_register_button)
                    textviewAuthAlreadyHaveAccountMessage.text =
                        getString(R.string.auth_layout_already_have_account_message)
                    textviewAuthSwitchScreenToLoginButton.text =
                        getString(R.string.auth_layout_sign_in_button)
                    textviewAuthForgotPassword.setInvisible()
                    setMultipleVisible(
                        buttonAuthRegisterByGoogle,
                        textviewAuthTextBetweenGoogleAndRegister,
                        textviewAuthWarningAboutTerms
                    )
                }
            }
        }
        viewModel.loginScreen.observe(this, uiObserver)
    }

    /**
     * Observe and show/Hide help buttons.
     */
    private fun observeHelpers() {
        helpersObserver = Observer {
            with(binding.flowAuthDebugButtons) {
                if (it) setVisible() else setGone()
            }
        }
        viewModel.helperButtonsVisible.observe(this, helpersObserver)
    }

    /**
     * Start main activity.
     *
     * @param email User email as String.
     */
    private fun startMainActivity(email: String) {
        val activity = requireActivity()
        val intentObject = Intent(activity, MainActivity::class.java)
        intentObject.putExtra(INTENT_EMAIL_ID, email)
        startActivity(intentObject)
        activity.overridePendingTransition(R.anim.zoom_in_inner, R.anim.zoom_in_outter)
        activity.finish()
    }


    /**
     * Set onClickListeners for email and password text input fields.
     */
    private fun setLoginPasswordFocusListeners() {
        with(binding) {
            textinputAuthEmail.setOnFocusChangeListener { _, focused ->
                if (focused && !viewModel.emailAlreadyFocused) viewModel.emailAlreadyFocused =
                    true
                if (!focused && viewModel.emailAlreadyFocused) textinputlayoutAuthEmail.helperText =
                    validEmail()
            }
            textinputAuthPassword.setOnFocusChangeListener { _, focused ->
                if (focused && !viewModel.passwordAlreadyFocused) viewModel.passwordAlreadyFocused =
                    true
                if (!focused && viewModel.passwordAlreadyFocused) textinputlayoutAuthPassword.helperText =
                    validPassword()
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
        val result = checkPasswordErrors(passwordText)
        // If pass all checks, return null or return errors.
        return if (result == "") null else result
    }

    /**
     * Check password for errors.
     */
    private fun checkPasswordErrors(passwordText: String): String {
        // Do all checks.
        var result = ""
        if (passwordText.length < MIN_PASSWORD_LENGTH) {                           // Minimum # chars.
            result += getString(
                R.string.auth_activity_password_warning_min_chars,
                MIN_PASSWORD_LENGTH
            )
        }

        val maxPasswordLength =
            resources.getInteger(R.integer.count_auth_password_max_length)  // Minimum # chars.
        if (passwordText.length > maxPasswordLength) {
            result =
                addErrorsDescriptionSeparator(result) + getString(
                    R.string.auth_activity_password_warning_max_chars,
                    maxPasswordLength
                )
        }
        if (!passwordText.matches(REGEX_ONE_UPPER_CHAR.toRegex())) {   // Minimum 1 UpperCase char.
            result =
                addErrorsDescriptionSeparator(result) + getString(R.string.auth_activity_password_warning_one_upper_char)
        }
        if (!passwordText.matches(REGEX_ONE_LOWER_CHAR.toRegex())) {   // Minimum 1 LowerCase char.
            result =
                addErrorsDescriptionSeparator(result) + getString(R.string.auth_activity_password_warning_one_lower_char)
        }
        if (!passwordText.matches(REGEX_ONE_SPECIAL_CHAR.toRegex())) { // Minimum 1 special char.
            result = addErrorsDescriptionSeparator(result) + getString(
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
        val email = binding.textinputAuthEmail.text.toString()
        val coroutineScope = CoroutineScope(Job())
        coroutineScope.launch(Dispatchers.IO) {
            dataStore.saveUserSata(email)
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
                "\n\n${getString(R.string.auth_activity_warning_message_email_title)}\n${textinputlayoutAuthEmail.helperText}"
            else ""
            message += if (!isPasswordCorrect)
                "\n\n${getString(R.string.auth_activity_warning_message_password_title)}\n${textinputlayoutAuthPassword.helperText}"
            else ""
        }

        // Show AlertDialog with error message.
        AlertDialog.Builder(requireContext())
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

        // Set onClickListeners().
        with(binding) {
            // Set onClickListener to message "Do you have account" to see help buttons.
            textviewAuthAlreadyHaveAccountMessage.setOnClickListener { viewModel.showOrHideHelpers() }

            // Fill fields button listener.
            buttonAuthFillLoginAndPass.setOnClickListener {
                textinputAuthEmail.setText(TEST_LOGIN)
                textinputAuthPassword.setText(TEST_PASSWORD)
                textinputlayoutAuthEmail.clearError()
                textinputlayoutAuthPassword.clearError()
            }

            // Clear fields button listener.
            buttonAuthClearLoginPassFields.setOnClickListener {
                textinputAuthEmail.setText("")
                textinputAuthPassword.setText("")
                textinputlayoutAuthEmail.clearError()
                textinputlayoutAuthPassword.clearError()
                checkboxAuthRememberMe.isChecked = false
            }

            // Language change button listener.
            buttonAuthChangeLanguage.setOnClickListener {
                goToChangeLanguage()
            }
        }
    }


    /**
     *  Open device language settings.
     */
    private fun goToChangeLanguage() {
        val intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
        with(intent) {
            addCategory(Intent.CATEGORY_DEFAULT)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
            addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
            startActivity(this)
        }
    }


    /**
     *  De-focus views, when user do click on background.
     */
    private fun backgroundFocusHandler() = with(binding) {
        root.setOnClickListener {
            textinputAuthEmail.clearFocus()
            textinputAuthPassword.clearFocus()
        }
    }

//    /**
//     * Save Instance State.
//     */
//    override fun onSaveInstanceState(outState: Bundle) {
//        with(binding) {
//            outState.putString(EMAIL_FIELD_STATE_KEY, textinputAuthEmail.text.toString())
//            outState.putString(PASSWORD_FIELD_STATE_KEY, textinputAuthPassword.text.toString())
//            outState.putInt(PASSWORD_VIEW_STATE_KEY, textinputlayoutAuthPassword.endIconMode)
//            outState.putBoolean(CHECKBOX_STATE_STATE_KEY, checkboxAuthRememberMe.isChecked)
//        }
//        super.onSaveInstanceState(outState)
//    }
//
//    /**
//     * Load Instance State.
//     */
//    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
//        super.onRestoreInstanceState(savedInstanceState)
//        with(binding) {
//            textinputAuthEmail.setText(savedInstanceState.getString(EMAIL_FIELD_STATE_KEY))
//            textinputAuthPassword.setText(savedInstanceState.getString(PASSWORD_FIELD_STATE_KEY))
//            textinputlayoutAuthPassword.endIconMode =
//                savedInstanceState.getInt(PASSWORD_VIEW_STATE_KEY)
//            checkboxAuthRememberMe.isChecked =
//                savedInstanceState.getBoolean(CHECKBOX_STATE_STATE_KEY)
//        }
//    }


}