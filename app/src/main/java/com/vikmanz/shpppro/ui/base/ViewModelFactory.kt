package com.vikmanz.shpppro.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.fragmentsnavigatortest.screens.base.BaseScreen
import com.vikmanz.shpppro.data.ContactsReposetory
import com.vikmanz.shpppro.navigator.MainNavigator
import com.vikmanz.shpppro.navigator.Navigator
import com.vikmanz.shpppro.ui.contacts.addcontact.AddContactDialogFragmentViewModel
import com.vikmanz.shpppro.ui.contacts.ContactsViewModel

class ViewModelFactory(
    private val contactsReposetory: ContactsReposetory,
    private val screen: BaseScreen,
    private val fragment: BaseFragment
) : ViewModelProvider.Factory {

//    @Suppress("UNCHECKED_CAST")
//    override fun <T : ViewModel> create(modelClass: Class<T>): T =
//        when (modelClass) {
//            ContactsViewModel::class.java -> ContactsViewModel(contactsReposetory) as T
//            AddContactDialogFragmentViewModel::class.java -> AddContactDialogFragmentViewModel(contactsReposetory) as T
//            else -> {
//                throw IllegalArgumentException("Unknown ViewModel class")
//            }
//        }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val hostActivity = fragment.requireActivity()
        val application = hostActivity.application
        val navigatorProvider = ViewModelProvider(
            hostActivity,
            ViewModelProvider.AndroidViewModelFactory(application)
        )
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
/**
 * Use this method for getting view-models from your fragments
 */
inline fun <reified VM : ViewModel> BaseFragment.screenViewModel(contactsReposetory: ContactsReposetory) = viewModels<VM> {
    val screen = requireArguments().getSerializable(ARG_SCREEN) as BaseScreen
    ViewModelFactory(contactsReposetory, screen, this)
}