package com.vikmanz.shpppro.presentation.auth.auth

import com.vikmanz.shpppro.presentation.base.BaseArgument
import com.vikmanz.shpppro.R
import com.vikmanz.shpppro.data.utils.EmailParser
import com.vikmanz.shpppro.databinding.FragmentMyProfileBinding
import com.vikmanz.shpppro.presentation.base.BaseFragment
import com.vikmanz.shpppro.presentation.utils.screenViewModel
import com.vikmanz.shpppro.utilits.extensions.log

class AuthFragment :
    BaseFragment<FragmentMyProfileBinding, AuthViewModel>(FragmentMyProfileBinding::inflate) {

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