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

        binding.tvPersonName.text = parseEmail(intent.getStringExtra(INTENT_EMAIL_ID))

        val button: AppCompatButton = binding.btnViewMyContacts
        button.setOnClickListener {
            finish()
        }

    }

    private fun parseEmail(fullEmail: String?): String {


        var personName = resources.getString(R.string.default_person_name)
        var registered = false
        Log.d("MyLog", "0")
        if (fullEmail!!.isNotEmpty()) {
            Log.d("MyLog", "1")
            if (fullEmail.indexOf('@') != -1) {
                Log.d("MyLog", "2")
                val firstPartEmail = fullEmail.substring(0, fullEmail.indexOf('@'))
                Log.d("MyLog", "$fullEmail")


                if (fullEmail.indexOf('.') == -1) {
                    Log.d("MyLog", "3")
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
                    Log.d("MyLog", "4")
                    Log.d("MyLog", "Name is [${personName}]")
                    registered = true

                } else {
                    Log.d("MyLog", "5")

                    if (fullEmail.indexOf('.') == -1) {
                        personName = firstPartEmail
                    } else {
                        personName = firstPartEmail
                            .split('.')
                            .joinToString(" ", transform = String::firstCharToUpperCase)

                    }
                    Log.d("MyLog", "6")
                    Log.d("MyLog", personName)
                    registered = true
                }
            } else {
                Log.d("MyLog", "7")
                personName = fullEmail
                registered = true
            }
        }

        Log.d("MyLog", "8")
        if (registered) {
            Log.d("MyLog", "9")
            binding.ivPerson.setImageResource(R.drawable.sample_avatar)
        }
        Log.d("MyLog", "10")
        return personName
    }


}


fun String.firstCharToUpperCase() = replaceFirstChar {
    if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
}