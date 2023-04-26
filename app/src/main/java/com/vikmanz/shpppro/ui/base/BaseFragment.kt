package com.vikmanz.shpppro.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<VBinding : ViewBinding, VM : BaseViewModel>(
    private val inflaterMethod: (LayoutInflater, ViewGroup?, Boolean) -> VBinding
) : Fragment() {

    //    protected abstract val viewModel: VM
    abstract val viewModel: VM

    private var _binding: VBinding? = null
    val binding get() = requireNotNull(_binding)

    abstract fun setListeners()
    abstract fun setObservers()
    abstract fun setStartUi()

//    abstract fun onReady(savedInstanceState: Bundle?)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflaterMethod.invoke(inflater, container, false)
        setListeners()
        setObservers()
        setStartUi()
        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}