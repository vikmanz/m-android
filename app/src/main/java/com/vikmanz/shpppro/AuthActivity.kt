package com.vikmanz.shpppro

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.AppCompatButton
import com.vikmanz.shpppro.databinding.ActivityAuthBinding

class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val button: AppCompatButton = binding.bRegisterByEmailPassword
        button.setOnClickListener {
            val changePage = Intent(this, MainActivity::class.java)
            startActivity(changePage)
        }


    }
}