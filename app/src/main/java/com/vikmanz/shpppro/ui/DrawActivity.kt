package com.vikmanz.shpppro.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.vikmanz.shpppro.utilits.CustomGoogleButton

class DrawActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        // init.
        super.onCreate(savedInstanceState)
        val draw2D = CustomGoogleButton(this)
        setContentView(draw2D)
    }

}