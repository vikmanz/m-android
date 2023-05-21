package com.vikmanz.shpppro.presentation.navigator

import android.content.Context
import androidx.navigation.NavController
import com.vikmanz.shpppro.presentation.navigator.interfaces.AuthNavControllerManager
import com.vikmanz.shpppro.utilits.extensions.log
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AuthNavGetter @Inject constructor(
    @ApplicationContext applicationContext: Context
) : AuthNavControllerManager {


    init {
        log("inside AuthNavGetter we have getter [$this]")
    }


    private var navigatorController: NavController? = null

    fun setNavController(navController: NavController) {
        navigatorController = navController
        log("AuthNavGetter [$this] was set to $navigatorController")
    }

    override fun getNavController(): NavController  = requireNotNull(navigatorController)

}