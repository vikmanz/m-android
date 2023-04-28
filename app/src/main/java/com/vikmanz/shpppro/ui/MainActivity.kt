package com.vikmanz.shpppro.ui

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.vikmanz.shpppro.R
import com.vikmanz.shpppro.constants.Preferences
import com.vikmanz.shpppro.constants.Preferences.USE_NAVIGATION_COMPONENT
import com.vikmanz.shpppro.databinding.ActivityMainBinding
import com.vikmanz.shpppro.navigator.ARG_SCREEN
import com.vikmanz.shpppro.navigator.MainNavigator
import com.vikmanz.shpppro.ui.my_profile.MyProfileFragment
import com.vikmanz.shpppro.ui.base.BaseActivity
import com.vikmanz.shpppro.utilits.log

/**
 * Class represents user main profile screen activity.
 */
class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    private val navigator by viewModels<MainNavigator> {
        ViewModelProvider.AndroidViewModelFactory(
            application
        )
    }

    /**
     * Main function, which used when activity was create.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startRootFragment(savedInstanceState)
    }


    private fun startRootFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            val args =
                MyProfileFragment.CustomArgument("MyContacts", "createdIn.Mainactiviti@gmail.com")
            log("if not null...")
            if (USE_NAVIGATION_COMPONENT) {
            log("... start by nav_graph")

                val startArguments = bundleOf(getString(R.string.safe_arg_id) to args)
                val navHostFragment = NavHostFragment.create(R.navigation.nav_graph, startArguments)

                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_main_container, navHostFragment)
                    .setPrimaryNavigationFragment(navHostFragment) // equivalent to app:defaultNavHost="true"
                    .commit()


            } else {
                log("... start by transactions")
                navigator.launchStartFragment(args)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        //onBackPressed()
        return true
    }

    override fun onResume() {
        super.onResume()
        navigator.whenActivityActive.mainActivity = this
    }

    override fun onPause() {
        super.onPause()
        navigator.whenActivityActive.mainActivity = null
    }
//
//    private val fragmentCallbacks = object : FragmentManager.FragmentLifecycleCallbacks() {
//        override fun onFragmentViewCreated(
//            fm: FragmentManager,
//            f: Fragment,
//            v: View,
//            savedInstanceState: Bundle?
//        ) {
//            if (supportFragmentManager.backStackEntryCount > 0) {
//                // more than 1 screen -> show back button in the toolbar
//                supportActionBar?.setDisplayHomeAsUpEnabled(true)
//            } else {
//                supportActionBar?.setDisplayHomeAsUpEnabled(false)
//            }
//
////            val result = navigator.result.value?.getValue() ?: return
////            if (f is BaseFragment) {
////                // has result that can be delivered to the screen's view-model
////                f.viewModel.onResult(result)
////            }
//        }
//    }

}

