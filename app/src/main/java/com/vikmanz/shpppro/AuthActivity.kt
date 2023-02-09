package com.vikmanz.shpppro

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import com.vikmanz.shpppro.constants.Constants.AUTO_LOGIN_TO_PROFILE
import com.vikmanz.shpppro.constants.Constants.CHECKBOX_STATE_STATE_KEY
import com.vikmanz.shpppro.constants.Constants.EMAIL_FIELD_STATE_KEY
import com.vikmanz.shpppro.constants.Constants.INTENT_EMAIL_ID
import com.vikmanz.shpppro.constants.Constants.LOGIN_VIEW_FIRST
import com.vikmanz.shpppro.constants.Constants.LOGIN_VIEW_STATE_KEY
import com.vikmanz.shpppro.constants.Constants.PASSWORD_FIELD_STATE_KEY
import com.vikmanz.shpppro.constants.Constants.PASSWORD_VIEW_STATE_KEY
import com.vikmanz.shpppro.dataSave.LoginDataStoreManager
import com.vikmanz.shpppro.databinding.ActivityAuthBinding
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding
    private var isLoginScreen = false
    private lateinit var loginData: LoginDataStoreManager


    private var autologinStatus = false
    private var helperButtonsVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        // init activity
        super.onCreate(savedInstanceState)

        binding = ActivityAuthBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        initHelpTesterButtons()

        if (LOGIN_VIEW_FIRST) changeRegisterLoginScreen()

        // save login data tests
        loginData = LoginDataStoreManager(this)
        checkUserLoginStatus()

        // Focus to bg and checkbox login functions.
        backgroundFocusHandler()
        // binding.checkBox.setOnClickListener { saveAuthData() }    // disable this to own email

        // Listeners to text fields and register button.
        emailFocusListener()
        passwordFocusListener()
        binding.bRegisterByEmailPassword.setOnClickListener { submitRegisterForm() }
        binding.alreadyHaveAccMessage.setOnClickListener { viewOrHideHelpTesterButtons() }

        // Change Register to Login and another.
        binding.alreadyHaveAccLink.setOnClickListener { changeRegisterLoginScreen() }
    }

    private fun viewOrHideHelpTesterButtons() {
        helperButtonsVisible = !helperButtonsVisible
        binding.apply {
            if (helperButtonsVisible) {
                bSave.visibility = View.VISIBLE
                bClear.visibility = View.VISIBLE
                bRestore.visibility = View.VISIBLE
                bFill.visibility = View.VISIBLE
            } else {
                bSave.visibility = View.GONE
                bClear.visibility = View.GONE
                bRestore.visibility = View.GONE
                bFill.visibility = View.GONE
            }
        }
    }

    // save state
    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(EMAIL_FIELD_STATE_KEY, binding.tiTextEmail.text.toString())
        outState.putString(PASSWORD_FIELD_STATE_KEY, binding.tiTextPassword.text.toString())
        outState.putInt(PASSWORD_VIEW_STATE_KEY, binding.tiLayoutPassword.endIconMode)
        outState.putBoolean(CHECKBOX_STATE_STATE_KEY, binding.checkBox.isChecked)
        outState.putBoolean(LOGIN_VIEW_STATE_KEY, autologinStatus)
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
        changeRegisterLoginScreen()
    }


    private fun doAutoLogin() {
        Log.d("MyLog", "Do Autologin!")
        restoreUserData()
        doRegister()
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun saveUserData() {
        val email = binding.tiTextEmail.text.toString()
        val password = binding.tiTextPassword.text.toString()
        val isAutologin = binding.checkBox.isChecked
        Log.d("MyLog", "email is $email, password is $password, isAutologin is $isAutologin")
        GlobalScope.launch {
            loginData.setUser(email, password, isAutologin)
        }
    }

    @Suppress("COMPATIBILITY_WARNING", "DEPRECATION")
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

    @Suppress("COMPATIBILITY_WARNING", "DEPRECATION")
    private fun checkUserLoginStatus() {
        loginData.userLoginStatusFlow.asLiveData().observe(this) {
            Log.d("MyLog", "Autologin status is $it")
            autologinStatus = it

            if (autologinStatus && AUTO_LOGIN_TO_PROFILE) doAutoLogin()

            Log.d("MyLog", "Autologin variable is $autologinStatus")
            if (autologinStatus) {
                Log.d("MyLog", "Fill fields!")
                restoreUserData()
            }
        }
    }


    private fun changeRegisterLoginScreen() {

        isLoginScreen = !isLoginScreen

        if (isLoginScreen) {
            binding.apply {
                tvHelloText.text = getString(R.string.helloText_signin)
                tvHelloSubText.text = getString(R.string.helloSubText_signin)
                tiLayoutPassword.isCounterEnabled = false
                forgotPassword.visibility = View.VISIBLE
                bRegisterByGoogle.visibility = View.GONE
                googleOrRegister.visibility = View.GONE
                bRegisterByEmailPassword.text = getString(R.string.registerButton_signin)
                warningAboutTerms.visibility = View.GONE
                alreadyHaveAccMessage.text = getString(R.string.already_have_an_account_signin)
                alreadyHaveAccLink.text = getString(R.string.sign_in_signin)
            }
        } else {
            binding.apply {
                tvHelloText.text = getString(R.string.helloText)
                tvHelloSubText.text = getString(R.string.helloSubText)
                tiLayoutPassword.isCounterEnabled = true
                forgotPassword.visibility = View.GONE
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
        Log.d("MyLog", "email: ${binding.tiTextEmail.text.toString()}")
        startActivity(intentObject)
        overridePendingTransition(R.anim.zoom_in_inner, R.anim.zoom_in_outter)
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
        message += if (binding.tiLayoutEmail.helperText != null) "\n\nEmail: ${binding.tiLayoutEmail.helperText}" else ""
        message += if (binding.tiLayoutPassword.helperText != null) "\n\nPassword: ${binding.tiLayoutPassword.helperText}" else ""

        Log.d("MyLog", message)
        AlertDialog.Builder(this)
            .setTitle("Invalid registration form!")
            .setMessage(message)
            .setPositiveButton("Okay") { _, _ ->
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
            return "Invalid email address"
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
                return "Minimum 8 Characters Password"
            }
            if (!passwordText.matches(".*[A-Z].*".toRegex())) {
                return "Must Contain 1 Upper-case Character"
            }
            if (!passwordText.matches(".*[a-z].*".toRegex())) {
                return "Must Contain 1 Lower-case Character"
            }
            if (!passwordText.matches(".*[@#\$%^&+=].*".toRegex())) {
                return "Must Contain 1 Special Character (@#\$%^&+=)"
            }
        }
        return null
    }

    private fun initHelpTesterButtons() {

        binding.bSave.setOnClickListener { saveUserData() }

        binding.bClear.setOnClickListener {
            binding.tiTextEmail.setText("")
            binding.tiTextPassword.setText("")
            binding.checkBox.isChecked = false
        }

        binding.bRestore.setOnClickListener { restoreUserData() }

        binding.bFill.setOnClickListener {
            binding.apply {
                tiTextEmail.setText(getString(R.string.my_main_email))
                tiTextPassword.setText(getString(R.string.my_main_password))
                tiLayoutEmail.helperText = null
                tiLayoutPassword.helperText = null
            }
        }
    }

}