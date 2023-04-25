package com.vikmanz.shpppro.ui.base

import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.example.fragmentsnavigatortest.screens.base.BaseArgs
import com.vikmanz.shpppro.navigator.ARG_SCREEN
import com.vikmanz.shpppro.navigator.MainNavigator

import com.vikmanz.shpppro.navigator.Navigator

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

        val constructor = modelClass.getConstructor(Navigator::class.java, BaseArgs::class.java)
        return constructor.newInstance(navigator, baseArgs)
    }
}

inline fun <reified VM : BaseViewModel, reified VBinding : ViewBinding> BaseFragment<VBinding, VM>.screenViewModel() = viewModels<VM> {
    val baseArgs = requireArguments().getSerializable(ARG_SCREEN) as BaseArgs
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) requireArguments().getSerializable(ARG_SCREEN, BaseArgs::class.java)
//        else @Suppress("DEPRECATION") requireArguments().getSerializable(ARG_SCREEN) as BaseArgs
    ViewModelFactory(baseArgs, this)
}


//    @Suppress("UNCHECKED_CAST")
//    override fun <T : ViewModel> create(modelClass: Class<T>): T =
//        when (modelClass) {
//            ContactsViewModel::class.java -> ContactsViewModel(contactsReposetory) as T
//            AddContactDialogFragmentViewModel::class.java -> AddContactDialogFragmentViewModel(
//                contactsReposetory
//            ) as T
//            else -> {
//                throw IllegalArgumentException("Unknown ViewModel class")
//            }
//        }

//
//class ViewModelFactoryTwo(
//    private val contactsReposetory: ContactsReposetory
//) : ViewModelProvider.Factory {
//
//    @Suppress("UNCHECKED_CAST")
//    override fun <T : ViewModel> create(modelClass: Class<T>): T =
//        when (modelClass) {
////            ContactsViewModel::class.java -> ContactsViewModel(contactsReposetory) as T
//            AddContactDialogFragmentViewModel::class.java -> AddContactDialogFragmentViewModel(
//                contactsReposetory
//            ) as T
//            else -> {
//                throw IllegalArgumentException("Unknown ViewModel class")
//            }
//        }
//}