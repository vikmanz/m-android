package com.vikmanz.shpppro.presentation.auth

import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.vikmanz.shpppro.R
import com.vikmanz.shpppro.databinding.ActivityAuthBinding
import com.vikmanz.shpppro.presentation.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

/**
 * Class represents SignIn or SignUp screen activity .
 */
@AndroidEntryPoint
class AuthActivity : BaseActivity<ActivityAuthBinding>(ActivityAuthBinding::inflate) {

    override lateinit var navController: NavController
    override fun initNavController() {
        val navHost = supportFragmentManager.findFragmentById(R.id.fragmentContainer_authActivity) as NavHostFragment
        navController = navHost.navController
    }

}