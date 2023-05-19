package com.vikmanz.shpppro.presentation.auth.login

import android.app.AlertDialog
import android.content.Intent
import android.provider.Settings
import android.util.Patterns
import androidx.lifecycle.Observer
import com.vikmanz.shpppro.App
import com.vikmanz.shpppro.R
import com.vikmanz.shpppro.presentation.base.BaseArgument
import com.vikmanz.shpppro.presentation.base.BaseFragment
import com.vikmanz.shpppro.presentation.main.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import com.vikmanz.shpppro.constants.Constants.INTENT_EMAIL_ID
import com.vikmanz.shpppro.constants.Constants.MIN_PASSWORD_LENGTH
import com.vikmanz.shpppro.databinding.FragmentLoginBinding
import com.vikmanz.shpppro.presentation.utils.extensions.clearError
import com.vikmanz.shpppro.presentation.utils.extensions.hideKeyboard
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

// TODO This fragment is too big. You should extract some logic to viewModel
class LoginFragment :
    BaseFragment<FragmentLoginBinding, LoginViewModel>(FragmentLoginBinding::inflate) {

    // TODO classes should be in the end of the file
    class CustomArgument : BaseArgument

    /**
     * Create ViewModel for this activity.
     */
    override val viewModel by screenAuthViewModel()

    private lateinit var uiObserver: Observer<Boolean>
    private lateinit var helpersObserver: Observer<Boolean>

    // Data Store
    private val dataStore = App.dataStore

    override fun setObservers() {
        observeUI()
        observeHelpers()
    }

    override fun setListeners() {
        with(binding) {
            buttonLoginRegisterByEmail.setOnClickListener { submitForm() }
            textViewLoginSwitchScreenToLoginButton.setOnClickListener { viewModel.swapLoginAndRegister() }
        }
        initHelpTesterButtons()
        setLoginPasswordFocusListeners()        // Listeners to fields and buttons.
        backgroundFocusHandler()                // de-focus fields when click on bg
    }

    /**
     * Observe and swap SignIn ans SignUp screens via changing text and visibility view on layout.
     */
    private fun observeUI() {
        uiObserver = Observer<Boolean> {
            with(binding) {
                if (it) {
                    textViewLoginHelloText.text =
                        getString(R.string.auth_activity_hello_text_login_screen)
                    textViewLoginHelloSubtext.text =
                        getString(R.string.auth_activity_hello_subtext_login_screen)
                    buttonLoginRegisterByEmail.text =
                        getString(R.string.auth_activity_register_button_login_screen)
                    textViewLoginAlreadyHaveAccountMessage.text =
                        getString(R.string.auth_activity_already_have_account_message_login_screen)
                    textViewLoginSwitchScreenToLoginButton.text =
                        getString(R.string.auth_activity_sign_in_button_login_screen)
                    textViewLoginForgotPasswordText.setVisible()
                    setMultipleGone(
                        buttonLoginRegisterByGoogle,
                        textViewLoginTextBetweenGoogleAndRegister,
                        textViewLoginWarningAboutTerms
                    )
                } else {
                    textViewLoginHelloText.text = getString(R.string.auth_layout_hello_text)
                    textViewLoginHelloSubtext.text =
                        getString(R.string.auth_layout_hello_subtext)
                    buttonLoginRegisterByEmail.text =
                        getString(R.string.auth_layout_register_button)
                    textViewLoginAlreadyHaveAccountMessage.text =
                        getString(R.string.auth_layout_already_have_account_message)
                    textViewLoginSwitchScreenToLoginButton.text =
                        getString(R.string.auth_layout_sign_in_button)
                    textViewLoginForgotPasswordText.setInvisible()
                    setMultipleVisible(
                        buttonLoginRegisterByGoogle,
                        textViewLoginTextBetweenGoogleAndRegister,
                        textViewLoginWarningAboutTerms
                    )
                }
            }

        }.also { viewModel.loginScreen.observe(this@LoginFragment, it) }

    }

    /**
     * Observe and show/Hide help buttons.
     */
    private fun observeHelpers() {
        helpersObserver = Observer<Boolean> {
            with(binding.flowLoginDebugButtons) {
                if (it) setVisible() else setGone()
            }
        }.also { viewModel.helperButtonsVisible.observe(this@LoginFragment, it) }
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

    //TODO errors don't work after change focus
    /**
     * Set onClickListeners for email and password text input fields.
     */
    private fun setLoginPasswordFocusListeners() {
        with(binding) {
            textInputLayoutLoginEmail.setOnFocusChangeListener { _, focused ->
                if (focused && !viewModel.emailAlreadyFocused)
                    viewModel.emailAlreadyFocused = true
                if (!focused && viewModel.emailAlreadyFocused)
                    textInputLayoutLoginEmail.helperText = validEmail()
            }
            textInputLayoutLoginPassword.setOnFocusChangeListener { _, focused ->
                if (focused && !viewModel.passwordAlreadyFocused)
                    viewModel.passwordAlreadyFocused = true
                if (!focused && viewModel.passwordAlreadyFocused)
                    textInputLayoutLoginPassword.helperText = validPassword()
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
        val emailText = binding.textInputLoginEmailField.text.toString()

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
        val passwordText = binding.textInputLoginPasswordField.text.toString()
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
            resources.getInteger(R.integer.count_loginFragment_passwordMaxLength)  // Minimum # chars.
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
            textInputLayoutLoginEmail.helperText = validEmail()
            textInputLayoutLoginPassword.helperText = validPassword()
            val isEmailCorrect = textInputLayoutLoginEmail.helperText == null
            val isPasswordCorrect = textInputLayoutLoginPassword.helperText == null

            // If valid, start MainActivity.
            if (isEmailCorrect && isPasswordCorrect) {
                // If check Remember, save data to Data Store.
                if (checkboxLoginRememberMe.isChecked) saveUserData()
                val emailText = textInputLoginEmailField.text.toString()
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
        val email = binding.textInputLoginEmailField.text.toString()
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
                "\n\n${getString(R.string.auth_activity_warning_message_email_title)}\n${textInputLayoutLoginEmail.helperText}"
            else ""
            message += if (!isPasswordCorrect)
                "\n\n${getString(R.string.auth_activity_warning_message_password_title)}\n${textInputLayoutLoginPassword.helperText}"
            else ""
        }

        // Show AlertDialog with error message.
        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.auth_activity_warning_message_invalid_register_form))
            .setMessage(message)
            .setPositiveButton(getString(R.string.auth_activity_warning_message_ok_button_text)) { _, _ ->
                with(binding) {
                    // Set focus to first field with error.
                    if (isEmailCorrect) textInputLoginEmailField.requestFocus()
                    else textInputLoginPasswordField.requestFocus()
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
            textViewLoginAlreadyHaveAccountMessage.setOnClickListener { viewModel.showOrHideHelpers() }

            // Fill fields button listener.
            buttonLoginFillLoginAndPass.setOnClickListener {
                textInputLoginEmailField.setText(TEST_LOGIN)
                textInputLoginPasswordField.setText(TEST_PASSWORD)
                textInputLayoutLoginEmail.clearError()
                textInputLayoutLoginPassword.clearError()
            }

            // Clear fields button listener.
            buttonLoginClearLoginPassFields.setOnClickListener {
                textInputLoginEmailField.setText("")
                textInputLoginPasswordField.setText("")
                textInputLayoutLoginEmail.clearError()
                textInputLayoutLoginPassword.clearError()
                checkboxLoginRememberMe.isChecked = false
            }

            // Language change button listener.
            buttonLoginChangeLanguage.setOnClickListener {
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
    //TODO add functionality to discard keyboard
    private fun backgroundFocusHandler() = with(binding) {
        root.setOnClickListener {
            textInputLoginEmailField.clearFocus()
            textInputLoginPasswordField.clearFocus()
            //hideKeyboard()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.loginScreen.removeObserver(uiObserver)
        viewModel.helperButtonsVisible.removeObserver(helpersObserver)
    }
}