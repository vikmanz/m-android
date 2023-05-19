package com.vikmanz.shpppro.presentation.auth

import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.vikmanz.shpppro.R
import com.vikmanz.shpppro.constants.USE_NAVIGATION_COMPONENT
import com.vikmanz.shpppro.databinding.ActivityAuthBinding
import com.vikmanz.shpppro.presentation.auth.splash_screen.SplashScreenFragment
import com.vikmanz.shpppro.presentation.base.BaseActivity
import com.vikmanz.shpppro.presentation.navigator.AuthNavigator
import com.vikmanz.shpppro.utilits.extensions.log

/**
 * Class represents SignIn or SignUp screen activity .
 */
class AuthActivity : BaseActivity<ActivityAuthBinding>(ActivityAuthBinding::inflate) {

    private val navigator by viewModels<AuthNavigator> {
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
        if (savedInstanceState == null) {
            val args =
                SplashScreenFragment.CustomArgument()
            log("if not null...")
            if (USE_NAVIGATION_COMPONENT) {
                log("... start by nav_graph")

                val startArguments = bundleOf(getString(R.string.safe_arg_id) to args)
                val navHostFragment = NavHostFragment.create(R.navigation.auth_nav_graph, startArguments)

                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer_authActivity, navHostFragment)
                    .setPrimaryNavigationFragment(navHostFragment) // equivalent to app:defaultNavHost="true"
                    .commit()


            } else {
                log("... start by transactions")
                navigator.launchStartFragment(args)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        //onBackPressed()
        return true
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