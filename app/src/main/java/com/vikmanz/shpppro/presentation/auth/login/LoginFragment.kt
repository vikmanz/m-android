package com.vikmanz.shpppro.presentation.auth.login

import android.app.AlertDialog
import android.content.Intent
import android.provider.Settings
import android.util.Patterns
import androidx.lifecycle.Observer
import com.vikmanz.shpppro.R
import com.vikmanz.shpppro.data.utils.PasswordErrorsChecker.checkPasswordErrors
import com.vikmanz.shpppro.databinding.FragmentLoginBinding
import com.vikmanz.shpppro.presentation.base.BaseArgument
import com.vikmanz.shpppro.presentation.base.BaseFragment
import com.vikmanz.shpppro.presentation.utils.CustomGoogleButton
import com.vikmanz.shpppro.presentation.utils.extensions.clearError
import com.vikmanz.shpppro.presentation.utils.extensions.hideKeyboard
import com.vikmanz.shpppro.presentation.utils.extensions.setGone
import com.vikmanz.shpppro.presentation.utils.extensions.setInvisible
import com.vikmanz.shpppro.presentation.utils.extensions.setMultipleGone
import com.vikmanz.shpppro.presentation.utils.extensions.setMultipleVisible
import com.vikmanz.shpppro.presentation.utils.extensions.setVisible
import com.vikmanz.shpppro.presentation.utils.extensions.startMainActivity
import com.vikmanz.shpppro.presentation.utils.screenAuthViewModel

class LoginFragment :
    BaseFragment<FragmentLoginBinding, LoginViewModel>(FragmentLoginBinding::inflate) {

    /**
     * Create ViewModel for this activity. Custom class need to change relevant type of viewModel in fabric.
     */
    class CustomArgument : BaseArgument

    override val viewModel by screenAuthViewModel()

    private lateinit var uiObserver: Observer<Boolean>
    private lateinit var helpersObserver: Observer<Boolean>

    override fun setObservers() {
        observeUI()
        observeHelpers()
    }

    override fun setListeners() {
        with(binding) {
            buttonLoginRegisterByEmail.setOnClickListener { checkForm() }
            textViewLoginSwitchScreenToLoginButton.setOnClickListener { viewModel.swapLoginAndRegister() }
            buttonLoginRegisterByGoogle.setOnClickListener {
                (buttonLoginRegisterByGoogle as CustomGoogleButton).setFunText()
            }
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
     * Set onClickListeners for email and password text input fields.
     */
    private fun setLoginPasswordFocusListeners() {
        with(binding) {
           textInputLoginEmailField.setOnFocusChangeListener { _, focused ->
                if (focused && !viewModel.emailAlreadyFocused) {
                    viewModel.emailAlreadyFocused = true
                } else if (!focused && viewModel.emailAlreadyFocused) {
                    validEmail()
                }
            }

            textInputLoginPasswordField.setOnFocusChangeListener { _, focused ->
                if (focused && !viewModel.passwordAlreadyFocused)
                    viewModel.passwordAlreadyFocused = true
                else if (!focused && viewModel.passwordAlreadyFocused)
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
        val emailText = binding.textInputLoginEmailField.text.toString()

        // Do check for standard Patterns.EMAIL_ADDRESS regex.
        if (!Patterns.EMAIL_ADDRESS.matcher(emailText).matches()) {
            val error = getString(R.string.auth_activity_warning_message_invalid_email)
            binding.textInputLayoutLoginEmail.helperText = error
            return error
        }
        // If pass check, return null.
        binding.textInputLayoutLoginEmail.helperText = null
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
        val result = checkPasswordErrors(passwordText, this)
        // If pass all checks, return null or return errors.
        val message = if (result == "") null else result
        binding.textInputLayoutLoginPassword.helperText = message
        return message
    }

    /**
     *  Submit Login/Register form and init MainActivity or show error messages.
     */
    private fun checkForm() {
        // Check correct or not.
        val isEmailCorrect = validEmail() == null
        val isPasswordCorrect = validPassword() == null
        // If correct - start main activity.
        if (isEmailCorrect && isPasswordCorrect) initMainActivity()
        // If have error - show error message.
        else showInvalidFormMessage(isEmailCorrect, isPasswordCorrect)
    }


    private fun initMainActivity() {
        with(binding) {
            if (checkboxLoginRememberMe.isChecked) saveUserData()  // If check Remember, save data to Data Store.
            val email = textInputLoginEmailField.text.toString()   // Take email text.
            startMainActivity(email)                               // Start main activity.
        }
    }

    /**
     *  Save user data from text input fields and language key from class variable to Data Store.
     */
    private fun saveUserData() {
        val email = binding.textInputLoginEmailField.text.toString()
        viewModel.saveUserEmailToDatastore(email)
    }

    /**
     *  Get error status of email and password, and show error message.
     */
    private fun showInvalidFormMessage(isEmailCorrect: Boolean, isPasswordCorrect: Boolean) {
        // Create error message.
        val message = createErrorMessage(isEmailCorrect, isPasswordCorrect)
        // Show AlertDialog with error message.
        showErrorAlertDialog(message, isEmailCorrect)
    }

    private fun createErrorMessage(isEmailCorrect: Boolean, isPasswordCorrect: Boolean): String {
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
        return message
    }

    private fun showErrorAlertDialog(message: String, isEmailCorrect: Boolean) {
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
    private fun backgroundFocusHandler() = with(binding) {
        root.setOnClickListener {
            textInputLoginEmailField.clearFocus()
            textInputLoginPasswordField.clearFocus()
            hideKeyboard(root)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.loginScreen.removeObserver(uiObserver)
        viewModel.helperButtonsVisible.removeObserver(helpersObserver)
    }

    companion object {
        /**
         * Constants.
         */
        const val TEST_LOGIN = "viktor.manza@gmail.com"
        private const val TEST_PASSWORD = "passwordE3@a"
    }

}