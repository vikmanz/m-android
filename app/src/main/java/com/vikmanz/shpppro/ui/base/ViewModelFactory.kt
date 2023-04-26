package com.vikmanz.shpppro.ui.base

import android.os.Build
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.vikmanz.shpppro.navigator.ARG_SCREEN
import com.vikmanz.shpppro.navigator.MainNavigator

import com.vikmanz.shpppro.navigator.Navigator
import com.vikmanz.shpppro.ui.profile.MyProfileFragment

class ViewModelFactory<VBinding : ViewBinding, VM : BaseViewModel>(
    private val baseArgs: BaseArgs,
    private val fragment: BaseFragment<VBinding, VM>
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val hostActivity = fragment.requireActivity()
        val application = hostActivity.application
        val navigatorProvider =
            ViewModelProvider(hostActivity, ViewModelProvider.AndroidViewModelFactory(application))
        val navigator = navigatorProvider[MainNavigator::class.java]

        val constructor =
            modelClass.getDeclaredConstructor(Navigator::class.java, baseArgs::class.java)
           // modelClass.getDeclaredConstructor(Navigator::class.java, MyProfileFragment.CustomArgs::class.java)
        return constructor.newInstance(navigator, baseArgs)
    }
}

inline fun <reified VM : BaseViewModel, reified VBinding : ViewBinding> BaseFragment<VBinding, VM>.screenViewModel() =
    viewModels<VM> {
        val baseArgs: BaseArgs = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requireArguments().getSerializable(ARG_SCREEN, BaseArgs::class.java) as BaseArgs
        } else {
            @Suppress("DEPRECATION") requireArguments().getSerializable(ARG_SCREEN) as BaseArgs
        }
        ViewModelFactory(baseArgs, this)
    }