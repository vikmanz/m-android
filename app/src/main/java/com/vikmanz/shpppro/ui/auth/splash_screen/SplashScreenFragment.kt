package com.vikmanz.shpppro.ui.auth.splash_screen

import android.annotation.SuppressLint
import androidx.fragment.app.viewModels
import com.vikmanz.shpppro.base.BaseFragment
import com.vikmanz.shpppro.databinding.FragmentSplashScreenBinding
import dagger.hilt.android.AndroidEntryPoint

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashScreenFragment :
    BaseFragment<FragmentSplashScreenBinding, SplashScreenViewModel>(FragmentSplashScreenBinding::inflate) {

    override val viewModel: SplashScreenViewModel by viewModels()
}