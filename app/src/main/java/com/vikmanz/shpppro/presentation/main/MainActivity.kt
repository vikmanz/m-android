package com.vikmanz.shpppro.presentation.main

import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.vikmanz.shpppro.databinding.ActivityMainBinding
import com.vikmanz.shpppro.presentation.base.BaseActivity
import com.vikmanz.shpppro.presentation.navigator.MainNavigator

/**
 * Class represents user main profile screen activity.
 */
class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    private val navigator by viewModels<MainNavigator> {
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

