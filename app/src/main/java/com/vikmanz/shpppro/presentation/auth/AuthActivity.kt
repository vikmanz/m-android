package com.vikmanz.shpppro.presentation.auth

import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.vikmanz.shpppro.R
import com.vikmanz.shpppro.databinding.ActivityAuthBinding
import com.vikmanz.shpppro.presentation.base.BaseActivity
import com.vikmanz.shpppro.presentation.navigator.AuthNavGetter
import com.vikmanz.shpppro.utilits.extensions.log
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * Class represents SignIn or SignUp screen activity .
 */
@AndroidEntryPoint
class AuthActivity : BaseActivity<ActivityAuthBinding>(ActivityAuthBinding::inflate) {

    @Inject lateinit var navGetter: AuthNavGetter
    override lateinit var navController: NavController
    override fun initNavController() {
        val navHost = supportFragmentManager.findFragmentById(R.id.fragmentContainer) as NavHostFragment
        log("outside in activity we have create saver [$navGetter] and controller [${navHost.navController}]")
        navGetter.setNavController(navHost.navController)
        log("and in activity set controller ${navGetter.getNavController()}")
    }

}