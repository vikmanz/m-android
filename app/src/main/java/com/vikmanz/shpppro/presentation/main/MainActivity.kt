package com.vikmanz.shpppro.presentation.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.vikmanz.shpppro.R
import com.vikmanz.shpppro.constants.Constants.INTENT_EMAIL_ID
import com.vikmanz.shpppro.constants.USE_NAVIGATION_COMPONENT
import com.vikmanz.shpppro.databinding.ActivityMainBinding
import com.vikmanz.shpppro.presentation.navigator.MainNavigator
import com.vikmanz.shpppro.presentation.main.my_profile.MyProfileFragment
import com.vikmanz.shpppro.presentation.base.BaseActivity
import com.vikmanz.shpppro.utilits.extensions.log

/**
 * Class represents user main profile screen activity.
 */
class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    private val navigator by viewModels<MainNavigator> {
        ViewModelProvider.AndroidViewModelFactory(
            application
        )
    }

    /**
     * Main function, which used when activity was create.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startRootFragment(savedInstanceState)
    }

    private fun startRootFragment(savedInstanceState: Bundle?) {
        val email = intent.getStringExtra(INTENT_EMAIL_ID).toString()
        if (savedInstanceState == null) {
            val args = MyProfileFragment.CustomArgument(email)
            if (USE_NAVIGATION_COMPONENT) {
                val startArguments = bundleOf(getString(R.string.safe_arg_id) to args)
                val navHostFragment =
                    NavHostFragment.create(R.navigation.main_nav_graph, startArguments)
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer_mainActivity, navHostFragment)
                    .setPrimaryNavigationFragment(navHostFragment) // equivalent to app:defaultNavHost="true"
                    .commit()
            } else {
                log("... start by transactions")
                navigator.launchStartFragment(args)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        navigator.whenActivityActive.currentActivity = this
    }

    override fun onPause() {
        super.onPause()
        navigator.whenActivityActive.currentActivity = null
    }

}

