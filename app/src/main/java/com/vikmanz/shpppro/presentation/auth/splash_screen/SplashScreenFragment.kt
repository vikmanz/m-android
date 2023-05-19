package com.vikmanz.shpppro.presentation.auth.splash_screen

import android.annotation.SuppressLint
import androidx.lifecycle.Observer
import com.vikmanz.shpppro.databinding.FragmentSplashScreenBinding
import com.vikmanz.shpppro.presentation.base.BaseArgument
import com.vikmanz.shpppro.presentation.base.BaseFragment
import com.vikmanz.shpppro.presentation.utils.extensions.startMainActivity
import com.vikmanz.shpppro.presentation.utils.screenAuthViewModel

@SuppressLint("CustomSplashScreen")
class SplashScreenFragment :
    BaseFragment<FragmentSplashScreenBinding, SplashScreenViewModel>(FragmentSplashScreenBinding::inflate) {


    /**
     * Create ViewModel for this activity. Custom class need to change relevant type of viewModel in fabric.
     */
    class CustomArgument : BaseArgument

    override val viewModel by screenAuthViewModel()

    private lateinit var autologinObserver: Observer<String>

    override fun setObservers() {
        setAutoLoginObserver()
    }

    /**
     * Check if user already save login-password, and do autologin if it's need.
     */
    private fun setAutoLoginObserver() {
        autologinObserver = Observer<String> {
            if (it != "") startMainActivity(it)
        }.also { viewModel.login.observe(this@SplashScreenFragment, it) }
    }

    /**
     * Remove observer.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.login.removeObserver(autologinObserver)
    }
}