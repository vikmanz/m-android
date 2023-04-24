package com.vikmanz.shpppro.ui

import android.os.Bundle
import com.vikmanz.shpppro.R
import com.vikmanz.shpppro.constants.Constants.INTENT_EMAIL_ID
import com.vikmanz.shpppro.databinding.ActivityMainBinding
import com.vikmanz.shpppro.ui.profile.ProfileFragment
import com.vikmanz.shpppro.ui.base.BaseActivity

/**
 * Class represents user main profile screen activity.
 */
class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    /**
     * Main function, which used when activity was create.
     */
    override fun onCreate(savedInstanceState: Bundle?) {

        // Init activity.
        super.onCreate(savedInstanceState)

        startRootFragment(savedInstanceState)
    }

    private fun startRootFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            val fragment = ProfileFragment.getInstance(
                userEmail = intent.getStringExtra(INTENT_EMAIL_ID) ?: getString(R.string.main_activity_person_email_hardcoded)
            )
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container_main_container, fragment)
                .commit()
        }
    }

}

