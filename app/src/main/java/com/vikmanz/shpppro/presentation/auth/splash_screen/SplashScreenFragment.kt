package com.vikmanz.shpppro.presentation.auth.splash_screen

import android.annotation.SuppressLint
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.vikmanz.shpppro.databinding.FragmentSplashScreenBinding
import com.vikmanz.shpppro.presentation.base.BaseFragment
import com.vikmanz.shpppro.presentation.utils.extensions.startMainActivity

@SuppressLint("CustomSplashScreen")
class SplashScreenFragment :
    BaseFragment<FragmentSplashScreenBinding>(FragmentSplashScreenBinding::inflate) {


    private val viewModel by viewModels<SplashScreenViewModel>()

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