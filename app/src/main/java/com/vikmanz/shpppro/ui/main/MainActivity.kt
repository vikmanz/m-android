package com.vikmanz.shpppro.ui.main

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.vikmanz.shpppro.R
import com.vikmanz.shpppro.ui.base.BaseActivity
import com.vikmanz.shpppro.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * Class represents user main profile screen activity.
 */
@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    private lateinit var navController: NavController

    /**
     * Add safe args to start destination fragment.
     */
    override fun setIncomingArguments(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) return
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.mainFragmentContainer) as NavHostFragment
        navController = navHostFragment.navController
        navController.setGraph(R.navigation.main_nav_graph, intent.extras)
    }

//    override fun onSupportNavigateUp(): Boolean {
//        return navController.navigateUp() || super.onSupportNavigateUp()
//    }

}