package com.vikmanz.shpppro.ui.main

import androidx.navigation.fragment.NavHostFragment
import com.vikmanz.shpppro.R
import com.vikmanz.shpppro.base.BaseActivity
import com.vikmanz.shpppro.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * Class represents user main profile screen activity.
 */
@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {
    /**
     * Add safe args to start destination fragment.
     */
    override fun setIncomingArguments() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.mainFragmentContainer) as NavHostFragment
        val navController = navHostFragment.navController
        navController.setGraph(R.navigation.main_nav_graph, intent.extras)
    }
}