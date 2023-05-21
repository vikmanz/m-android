package com.vikmanz.shpppro.presentation.main

import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.vikmanz.shpppro.R
import com.vikmanz.shpppro.databinding.ActivityMainBinding
import com.vikmanz.shpppro.presentation.base.BaseActivity
import com.vikmanz.shpppro.presentation.navigator.MainNavGetter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * Class represents user main profile screen activity.
 */
@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    @Inject lateinit var navGetter: MainNavGetter
    override lateinit var navController: NavController
    override fun initNavController() {
        val navHost = supportFragmentManager.findFragmentById(R.id.fragmentContainer) as NavHostFragment
        navGetter.setNavController(navHost.navController)
    }


}

