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

    abstract fun setStartUI()
    abstract fun setObservers()
    abstract fun setListeners()
    abstract fun onReady(savedInstanceState: Bundle?)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflaterMethod.invoke(inflater, container, false)
        setStartUI()
        setListeners()
        setObservers()
        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        observeNavigation()
//        onReady(savedInstanceState)
//    }
//
//    private fun observeNavigation() {
//        viewModel.navigation.observeNonNull(viewLifecycleOwner) {
//            it.getContentIfNotHandled()?.let { navigationCommand ->
//                handleNavigation(navigationCommand)
//            }
//        }
//    }
//
//    private fun handleNavigation(navCommand: NavigationCommand) {
//        when (navCommand) {
//            is NavigationCommand.ToDirection -> findNavController().navigate(navCommand.directions)
//            is NavigationCommand.Back -> findNavController().navigateUp()
//        }
//    }
//
//        override fun onDestroyView() {
//        _binding = null
//        super.onDestroyView()
//    }
//
//}
//
//
//fun <T> LiveData<T>.observeNonNull(owner: LifecycleOwner, observer: (t: T) -> Unit) {
//    this.observe(
//        owner
//    ) {
//        it?.let(observer)
//    }
//}