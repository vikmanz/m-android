package com.vikmanz.shpppro.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vikmanz.shpppro.data.ContactsReposetory
import com.vikmanz.shpppro.ui.contacts.addcontact.AddContactDialogFragmentViewModel
import com.vikmanz.shpppro.ui.contacts.ContactsViewModel

class ViewModelFactory(
    private val contactsReposetory: ContactsReposetory
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when (modelClass) {
            ContactsViewModel::class.java -> ContactsViewModel(contactsReposetory) as T
            AddContactDialogFragmentViewModel::class.java -> AddContactDialogFragmentViewModel(contactsReposetory) as T
            else -> {
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
}


/*
/**
 * This view-model factory allows creating view-models which have constructor with 2
 * arguments: [Navigator] and some subclass of [BaseScreen].
 */
class ViewModelFactory(
    private val screen: BaseScreen,
    private val fragment: BaseFragment
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val hostActivity = fragment.requireActivity()
        val application = hostActivity.application
        val navigatorProvider = ViewModelProvider(hostActivity, AndroidViewModelFactory(application))
        val navigator = navigatorProvider[MainNavigator::class.java]

        // if you need to create a view model with some other arguments -> you may
        // use 'constructors' field for searching the desired constructor
        val constructor = modelClass.getConstructor(Navigator::class.java, screen::class.java)
        return constructor.newInstance(navigator, screen)
    }

}

/**
 * Use this method for getting view-models from your fragments
 */
inline fun <reified VM : ViewModel> BaseFragment.screenViewModel() = viewModels<VM> {


    val screen = if (IS_NAVIGATION) {
        args.MyArg
    }
    else {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) requireArguments().getSerializable(ARG_SCREEN, BaseScreen::class.java)
        else @Suppress("DEPRECATION") requireArguments().getSerializable(ARG_SCREEN) as? BaseScreen
    }
    ViewModelFactory(screen ?: HelloFragment.Screen("error"), this)

 */