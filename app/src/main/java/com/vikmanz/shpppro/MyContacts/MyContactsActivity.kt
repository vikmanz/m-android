package com.vikmanz.shpppro.MyContacts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.vikmanz.shpppro.databinding.ActivityMyContactsBinding

/**
 * Class represents MyContacts screen activity.
 */
class MyContactsActivity : AppCompatActivity() {

    // Binding, Data Store and Coroutine Scope variables.
    private lateinit var binding: ActivityMyContactsBinding

    /**
     * Main function, which used when activity was create.
     */
    override fun onCreate(savedInstanceState: Bundle?) {

        // Init activity.
        super.onCreate(savedInstanceState)

        // Others init operations.
        binding = ActivityMyContactsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Back button.
        binding.btnBack.setOnClickListener { finish() }

    }


}