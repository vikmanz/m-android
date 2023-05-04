package com.vikmanz.shpppro.presentation.auth.splash_screen

import com.vikmanz.shpppro.presentation.base.BaseArgument
import com.vikmanz.shpppro.databinding.FragmentMyProfileBinding
import com.vikmanz.shpppro.presentation.base.BaseFragment
import com.vikmanz.shpppro.presentation.utils.screenViewModel

class SplashScreenFragment :
    BaseFragment<FragmentMyProfileBinding, SplashScreenViewModel>(FragmentMyProfileBinding::inflate) {

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