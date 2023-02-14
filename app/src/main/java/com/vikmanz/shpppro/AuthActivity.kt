package com.vikmanz.shpppro

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.asLiveData
import com.vikmanz.shpppro.constants.Constants.AUTO_LOGIN_TO_PROFILE
import com.vikmanz.shpppro.constants.Constants.CHECKBOX_STATE_STATE_KEY
import com.vikmanz.shpppro.constants.Constants.EMAIL_FIELD_STATE_KEY
import com.vikmanz.shpppro.constants.Constants.INTENT_EMAIL_ID
import com.vikmanz.shpppro.constants.Constants.INTENT_LANG_ID
import com.vikmanz.shpppro.constants.Constants.LANGUAGE_STATE_KEY
import com.vikmanz.shpppro.constants.Constants.LOGIN_VIEW_FIRST
import com.vikmanz.shpppro.constants.Constants.LOGIN_VIEW_STATE_KEY
import com.vikmanz.shpppro.constants.Constants.PASSWORD_FIELD_STATE_KEY
import com.vikmanz.shpppro.constants.Constants.PASSWORD_VIEW_STATE_KEY
import com.vikmanz.shpppro.constants.Constants.VIEW_HELP_BUTTONS
import com.vikmanz.shpppro.dataSave.LoginDataStoreManager
import com.vikmanz.shpppro.databinding.ActivityAuthBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*

class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding
    private var isLoginScreen = false
    private lateinit var loginData: LoginDataStoreManager
    private val coroutineScope: CoroutineScope = CoroutineScope(Job())

    private var autologinStatus = false
    private var isEnglish = true
    private var helperButtonsVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        // init activity
        super.onCreate(savedInstanceState)

        loginData = LoginDataStoreManager(this)

        binding = ActivityAuthBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        if (LOGIN_VIEW_FIRST) changeRegisterLoginScreen()

        // save login data tests

        checkUserLoginStatus()

        initHelpTesterButtons()
        // Focus to bg and checkbox login functions.
        backgroundFocusHandler()
        // binding.checkBox.setOnClickListener { saveAuthData() }    // disable this to own email

        // Listeners to text fields and register button.
        emailFocusListener()
        passwordFocusListener()
        binding.bRegisterByEmailPassword.setOnClickListener { submitRegisterForm() }

        // Help buttons.
        if (VIEW_HELP_BUTTONS) viewOrHideHelpTesterButtons()
        binding.alreadyHaveAccMessage.setOnClickListener { viewOrHideHelpTesterButtons() }

        // Change Register to Login and another.
        binding.alreadyHaveAccLink.setOnClickListener { changeRegisterLoginScreen() }

    }

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

    private fun viewOrHideHelpTesterButtons() {
        helperButtonsVisible = !helperButtonsVisible
        binding.flowTestButtons.visibility = if (helperButtonsVisible) View.VISIBLE else View.GONE
    }

    // save state
    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(EMAIL_FIELD_STATE_KEY, binding.tiTextEmail.text.toString())
        outState.putString(PASSWORD_FIELD_STATE_KEY, binding.tiTextPassword.text.toString())
        outState.putInt(PASSWORD_VIEW_STATE_KEY, binding.tiLayoutPassword.endIconMode)
        outState.putBoolean(CHECKBOX_STATE_STATE_KEY, binding.checkBox.isChecked)
        outState.putBoolean(LOGIN_VIEW_STATE_KEY, autologinStatus)
        outState.putBoolean(LANGUAGE_STATE_KEY, isEnglish)
        super.onSaveInstanceState(outState)
    }

    // load state
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        binding.apply {
            tiTextEmail.setText(savedInstanceState.getString(EMAIL_FIELD_STATE_KEY))
            tiTextPassword.setText(savedInstanceState.getString(PASSWORD_FIELD_STATE_KEY))
            tiLayoutPassword.endIconMode = savedInstanceState.getInt(PASSWORD_VIEW_STATE_KEY)
            checkBox.isChecked = savedInstanceState.getBoolean(CHECKBOX_STATE_STATE_KEY)
        }
        autologinStatus = savedInstanceState.getBoolean(LOGIN_VIEW_STATE_KEY)
        isEnglish = savedInstanceState.getBoolean(LANGUAGE_STATE_KEY)
        setLocale()
    }


    private fun saveUserData() {
        val email = binding.tiTextEmail.text.toString()
        val password = binding.tiTextPassword.text.toString()
        val isAutologin = binding.checkBox.isChecked

        coroutineScope.launch(Dispatchers.IO) {
            loginData.saveUserSata(email, password, isAutologin)
        }
    }


    private fun doAutoLogin() {
        restoreUserData()
        doRegister()
    }

    private fun checkUserLoginStatus() {
        loginData.userLoginStatusFlow.asLiveData().observe(this) {
            autologinStatus = it
            if (autologinStatus && AUTO_LOGIN_TO_PROFILE) doAutoLogin()
            if (autologinStatus) {
                restoreUserData()
            }
        }
    }

    private fun restoreUserData() {
        loginData.userNameFlow.asLiveData().observe(this) {
            binding.tiTextEmail.setText(it)
        }
        loginData.userPasswordFlow.asLiveData().observe(this) {
            binding.tiTextPassword.setText(it)
        }
        loginData.userLoginStatusFlow.asLiveData().observe(this) {
            binding.checkBox.isChecked = it
        }
    }


    private fun changeRegisterLoginScreen() {

        isLoginScreen = !isLoginScreen

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


    private fun doRegister() {
        val intentObject = Intent(this, MainActivity::class.java)
        intentObject.putExtra(INTENT_EMAIL_ID, binding.tiTextEmail.text.toString())
        intentObject.putExtra(INTENT_LANG_ID, isEnglish)
        startActivity(intentObject)
        overridePendingTransition(R.anim.zoom_in_inner, R.anim.zoom_in_outter)
        finish()
    }


    private fun backgroundFocusHandler() {
        binding.root.setOnClickListener { binding.tiTextEmail.clearFocus() }
    }

    private fun submitRegisterForm() {

        binding.tiLayoutEmail.helperText = validEmail()
        binding.tiLayoutPassword.helperText = validPassword()

        val isValid =
            binding.tiLayoutEmail.helperText == null && binding.tiLayoutPassword.helperText == null

        if (isValid) {
            if (binding.checkBox.isChecked) saveUserData()
            doRegister()
        } else {
            invalidForm()
        }
    }


    private fun invalidForm() {

        var message = ""
        message += if (binding.tiLayoutEmail.helperText != null)
            "\n\n${getString(R.string.warning_emailTitle)} ${binding.tiLayoutEmail.helperText}"
            else ""
        message += if (binding.tiLayoutPassword.helperText != null)
            "\n\n${getString(R.string.warning_passwordTitle)} ${binding.tiLayoutPassword.helperText}"
            else ""

        AlertDialog.Builder(this)
            .setTitle(getString(R.string.warningMessage_InvalidRegisterForm))
            .setMessage(message)
            .setPositiveButton(getString(R.string.warningMessage_OkButtonOnAllert)) { _, _ ->
                if (binding.tiLayoutEmail.helperText != null) {
                    binding.tiTextEmail.requestFocus()
                } else {
                    binding.tiTextPassword.requestFocus()
                }
            }
            .show()
    }

    private fun emailFocusListener() {
        binding.tiTextEmail.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.tiLayoutEmail.helperText = validEmail()
            }
        }
    }

    private fun validEmail(): String? {
        val emailText = binding.tiTextEmail.text.toString()
        if (!Patterns.EMAIL_ADDRESS.matcher(emailText).matches()) {
            return getString(R.string.warningMessage_InvalidEmailAdress)
        }
        return null
    }

    private fun passwordFocusListener() {
        binding.tiTextPassword.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.tiLayoutPassword.helperText = validPassword()
            }
        }
    }

    private fun validPassword(): String? {
        if (!binding.checkBox.isChecked) {
            val passwordText = binding.tiTextPassword.text.toString()
            if (passwordText.length < 8) {
                return getString(R.string.passCheckWarning_min8chars)
            }
            if (!passwordText.matches(getString(R.string.regex_pass_OneUpperChar).toRegex())) {
                return getString(R.string.passCheckWarning_OneUpperChar)
            }
            if (!passwordText.matches(getString(R.string.regex_pass_OneLowerChar).toRegex())) {
                return getString(R.string.passCheckWarning_OneLowerChar)
            }
            if (!passwordText.matches(getString(R.string.regex_pass_OneSpecialChar).toRegex())) {
                return getString(R.string.passCheckWarning_OneSpecialChar)
            }
        }
        return null
    }

    private fun initHelpTesterButtons() {

        // Fill fields button.
        binding.btnForTest1.setOnClickListener {
            binding.apply {
                tiTextEmail.setText(getString(R.string.my_main_email))
                tiTextPassword.setText(getString(R.string.my_main_password))
                tiLayoutEmail.helperText = null
                tiLayoutPassword.helperText = null
            }
        }

        // Clear fields button.
        binding.btnForTest2.setOnClickListener {
            binding.tiTextEmail.setText("")
            binding.tiTextPassword.setText("")
            binding.checkBox.isChecked = false
        }

        // Language change button.
        binding.btnForTest3.setOnClickListener {
            isEnglish = !isEnglish
            setLocale()
            this.recreate()
        }

    }

}