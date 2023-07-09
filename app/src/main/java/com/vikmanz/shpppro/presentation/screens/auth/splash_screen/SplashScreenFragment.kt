package com.vikmanz.shpppro.presentation.screens.auth.splash_screen

import android.annotation.SuppressLint
import androidx.fragment.app.viewModels
import com.vikmanz.shpppro.databinding.FragmentSplashScreenBinding
import com.vikmanz.shpppro.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashScreenFragment :
    BaseFragment<FragmentSplashScreenBinding, SplashScreenViewModel>(FragmentSplashScreenBinding::inflate) {

    override val viewModel: SplashScreenViewModel by viewModels()
}