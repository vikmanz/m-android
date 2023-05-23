package com.vikmanz.shpppro.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavArgs
import androidx.viewbinding.ViewBinding

abstract class BaseFragmentWithArgs<VBinding : ViewBinding, VM : BaseViewModelWithArgs<Args>, Args : NavArgs> (
    inflaterMethod: (LayoutInflater, ViewGroup?, Boolean) -> VBinding
) : BaseFragment<VBinding, VM>(inflaterMethod) {

    protected abstract val args: Args

    override fun setIncomingArguments() {
        viewModel.setNavArgs(args)
    }

}