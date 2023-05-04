package com.vikmanz.shpppro.presentation.utils

import android.os.Build
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.vikmanz.shpppro.R
import com.vikmanz.shpppro.constants.USE_NAVIGATION_COMPONENT
import com.vikmanz.shpppro.navigator.ARG_SCREEN
import com.vikmanz.shpppro.navigator.MainNavigator

import com.vikmanz.shpppro.navigator.Navigator
import com.vikmanz.shpppro.presentation.base.*

class ViewModelFactory<VBinding : ViewBinding, VM : BaseViewModel>(
    private val baseArgument: BaseArgument?,
    private val fragment: BaseFragment<VBinding, VM>
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val hostActivity = fragment.requireActivity()
        val application = hostActivity.application
        val navigatorProvider =
            ViewModelProvider(hostActivity, ViewModelProvider.AndroidViewModelFactory(application))
        val navigator = navigatorProvider[MainNavigator::class.java]

        val arguments = if (baseArgument == null) null else baseArgument::class.java
        val constructor =
            modelClass.getDeclaredConstructor(Navigator::class.java, arguments)
        return constructor.newInstance(navigator, baseArgument)
    }
}


inline fun <reified VM : BaseViewModel, reified VBinding : ViewBinding> BaseFragment<VBinding, VM>.screenViewModel() =
    viewModels<VM> {

        val key = if (USE_NAVIGATION_COMPONENT) getString(R.string.safe_arg_id) else ARG_SCREEN

        val baseArgument: BaseArgument =
                   if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                       requireArguments().getSerializable(
                           key,
                           BaseArgument::class.java
                       ) as BaseArgument
                   } else {
                       @Suppress("DEPRECATION") requireArguments().getSerializable(key) as BaseArgument
                   }
        ViewModelFactory(baseArgument, this)
    }