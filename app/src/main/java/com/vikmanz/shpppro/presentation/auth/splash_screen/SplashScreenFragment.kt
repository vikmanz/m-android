package com.vikmanz.shpppro.presentation.auth.splash_screen

import android.annotation.SuppressLint
import com.vikmanz.shpppro.presentation.base.BaseArgument
import com.vikmanz.shpppro.databinding.FragmentMyProfileBinding
import com.vikmanz.shpppro.databinding.FragmentSplashScreenBinding
import com.vikmanz.shpppro.presentation.base.BaseFragment
import com.vikmanz.shpppro.presentation.utils.screenViewModel

@SuppressLint("CustomSplashScreen")
class SplashScreenFragment :
    BaseFragment<FragmentSplashScreenBinding, SplashScreenViewModel>(FragmentSplashScreenBinding::inflate) {

    class CustomArgument() : BaseArgument

    /**
     * Create ViewModel for this activity.
     */
    override val viewModel by screenViewModel()

    //val loginData = DataStoreManager(requireContext()) //in onCreate

    override fun setStartUi() { }

    override fun setObservers() { }

    override fun setListeners() { }

}