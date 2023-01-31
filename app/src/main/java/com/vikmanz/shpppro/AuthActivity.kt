package com.vikmanz.shpppro

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import com.vikmanz.shpppro.constants.Constants.INTENT_EMAIL_ID
import com.vikmanz.shpppro.databinding.ActivityAuthBinding


class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val emailId = binding.tiTextEmailAddress as AppCompatEditText
        //val pass = binding.tiTextEmailAddress as AppCompatEditText
        val googleButton = binding.bRegisterByGoogle
        val button: AppCompatButton = binding.bRegisterByEmailPassword

        button.setOnClickListener {
            val intentObject = Intent(this, MainActivity::class.java)
            intentObject.putExtra(INTENT_EMAIL_ID, "${emailId.text}")
            startActivity(intentObject)
        }

        googleButton.setOnClickListener {
            if (emailId.text.toString().trim() == "") {
                emailId.error = "Enter E-Mail!"
            }
        }

        emailId.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                emailId.error = null
            }

            override fun beforeTextChanged(
                s: CharSequence, start: Int, count: Int,
                after: Int
            ) {

            }

            override fun afterTextChanged(s: Editable) {
                emailId.error = null
            }
        })


    }
}