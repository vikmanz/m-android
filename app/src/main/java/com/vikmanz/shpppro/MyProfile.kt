package com.vikmanz.shpppro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.AppCompatButton

class MyProfile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_profile)

        val button: AppCompatButton = findViewById(R.id.btnViewMyContacts)




        button.setOnClickListener {
            finish()
        }

    }

}