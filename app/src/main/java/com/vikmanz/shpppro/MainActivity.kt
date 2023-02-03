package com.vikmanz.shpppro

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.widget.AppCompatButton
import com.vikmanz.shpppro.databinding.ActivityMainBinding
import com.vikmanz.shpppro.constants.Constants.INTENT_EMAIL_ID
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        Log.d("MyLog", "-3")
        val emailToParse = intent.getStringExtra(INTENT_EMAIL_ID).toString()
        Log.d("MyLog", "-2: $emailToParse")
        binding.tvPersonName.text = parseEmail(emailToParse)
        Log.d("MyLog", "-1")

        val button: AppCompatButton = binding.btnViewMyContacts
        button.setOnClickListener {
            finish()
        }

    }

    private fun parseEmail(fullEmail: String): String {

        Log.d("MyLog", "0: $fullEmail")
        val personName: String

        if (fullEmail == getString(R.string.guest_email)) return getString(R.string.guest_name_surname)
        Log.d("MyLog", "1: $fullEmail")

        val firstPartEmail = fullEmail.substring(0, fullEmail.indexOf('@'))
        Log.d("MyLog", "email first part: $firstPartEmail")

        if (firstPartEmail.indexOf('.') == -1) {
            Log.d("MyLog", "2")
            val regex = "[A-Z]".toRegex()
            val match: MatchResult? = regex.find(firstPartEmail.substring(1))

            personName = if (match == null) {
                firstPartEmail
            } else {
                val surnameStartIndex = match.range.first + 1
                "${
                    firstPartEmail.substring(0, surnameStartIndex).replaceFirstChar {
                        if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
                    }
                } ${firstPartEmail.substring(surnameStartIndex)}"
            }
            Log.d("MyLog", "3")

        } else {
            Log.d("MyLog", "4")
            personName = firstPartEmail
                .split('.')
                .joinToString(" ", transform = String::firstCharToUpperCase)

            Log.d("MyLog", "5")
        }

        Log.d("MyLog", "6")
        binding.ivPerson.setImageResource(R.drawable.sample_avatar)

        Log.d("MyLog", "Name is [${personName}]")
        return personName
    }
}


fun String.firstCharToUpperCase() = replaceFirstChar {
    if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
}