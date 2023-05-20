package com.vikmanz.shpppro.presentation.auth

import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.vikmanz.shpppro.databinding.ActivityAuthBinding
import com.vikmanz.shpppro.presentation.base.BaseActivity
import com.vikmanz.shpppro.presentation.navigator.AuthNavigator

/**
 * Class represents SignIn or SignUp screen activity .
 */
class AuthActivity : BaseActivity<ActivityAuthBinding>(ActivityAuthBinding::inflate) {

    private val navigator by viewModels<AuthNavigator> {
        ViewModelProvider.AndroidViewModelFactory(
            application
        )
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