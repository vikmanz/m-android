package com.vikmanz.shpppro

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.AppCompatButton

class SignUp : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        val button: AppCompatButton = findViewById(R.id.bRegisterByEmailPassword)




        button.setOnClickListener {
            val changePage = Intent(this, MyProfile::class.java)
            startActivity(changePage)
        }

    }



}