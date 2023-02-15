package com.vikmanz.shpppro.myContactsActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.vikmanz.shpppro.AuthActivity
import com.vikmanz.shpppro.R
import com.vikmanz.shpppro.constants.Constants
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
        binding.btnBack.setOnClickListener {
            startAuthActivity()
        }

    }

    private fun startAuthActivity() {
        val intentObject = Intent(this, AuthActivity::class.java)
        intentObject.putExtra(Constants.INTENT_LANG_ID, false)
        startActivity(intentObject)
        overridePendingTransition(R.anim.zoom_in_inner, R.anim.zoom_in_outter)
        finish()
    }


}