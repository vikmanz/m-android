package com.vikmanz.shpppro.presentation.navigator

import android.content.Context
import androidx.navigation.NavController
import com.vikmanz.shpppro.presentation.navigator.interfaces.MainNavControllerManager
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class MainNavGetter @Inject constructor(
    @ApplicationContext applicationContext: Context
) : MainNavControllerManager {


    private var navigatorController: NavController? = null

    fun setNavController(navController: NavController) {
        navigatorController = navController
    }

    override fun getNavController(): NavController  = requireNotNull(navigatorController)

}