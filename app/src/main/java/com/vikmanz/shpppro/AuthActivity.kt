package com.vikmanz.shpppro

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import androidx.appcompat.app.AppCompatActivity
import com.vikmanz.shpppro.constants.Constants.INTENT_EMAIL_ID
import com.vikmanz.shpppro.constants.Constants.PASS_CHECK
import com.vikmanz.shpppro.databinding.ActivityAuthBinding


class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        emailFocusListener()
        passwordFocusListener()

        binding.bRegisterByEmailPassword.setOnClickListener { submitRegisterForm() }

    }

    private fun submitRegisterForm() {

        binding.tiLayoutEmail.helperText = validEmail()
        binding.tiLayoutPassword.helperText = validPassword()

        val isValid =
            binding.tiLayoutEmail.helperText == null && binding.tiLayoutPassword.helperText == null

        if (isValid) {
            doRegister()
        } else {
            invalidForm()
        }

    }

    private fun doRegister() {
        val intentObject = Intent(this, MainActivity::class.java)
        intentObject.putExtra(INTENT_EMAIL_ID, binding.tiTextEmail.text.toString())
        Log.d("MyLog", "email: ${binding.tiTextEmail.text.toString()}")
        startActivity(intentObject)
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
        if (PASS_CHECK) {
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
}