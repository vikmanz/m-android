package com.vikmanz.shpppro.presentation.main

import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.vikmanz.shpppro.R
import com.vikmanz.shpppro.databinding.ActivityMainBinding
import com.vikmanz.shpppro.presentation.base.BaseActivity

/**
 * Class represents user main profile screen activity.
 */
class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    override lateinit var navController: NavController
    override fun initNavController() {
        val navHost = supportFragmentManager.findFragmentById(R.id.fragmentContainer) as NavHostFragment
        navController = navHost.navController
    }


}

