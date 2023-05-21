package com.vikmanz.shpppro.presentation.navigator

import androidx.navigation.NavController
import com.vikmanz.shpppro.presentation.navigator.interfaces.MainNavControllerManager

class MainNavGetter : MainNavControllerManager {


    private var navigatorController: NavController? = null

    fun setNavController(navController: NavController) {
        navigatorController = navController
    }

    override fun getNavController(): NavController  = requireNotNull(navigatorController)

}