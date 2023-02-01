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


        val fullEmail: String? = intent.getStringExtra(INTENT_EMAIL_ID)

        if (fullEmail!!.isNotEmpty()) {
            Log.d("MyLog", "3")
            val firstPartEmail = fullEmail.substring(0, fullEmail.indexOf('@'))
            Log.d("MyLog", "$fullEmail")
            val regex = "[A-Z]".toRegex()

            val personName: String
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

            Log.d("MyLog", "Name is [${personName}]")
            binding.tvPersonName.text = personName
        } else {
            binding.tvPersonName.text = "Vasa Pun"
        }


        val button: AppCompatButton = binding.btnViewMyContacts
        button.setOnClickListener {
            finish()
        }

    }
}

