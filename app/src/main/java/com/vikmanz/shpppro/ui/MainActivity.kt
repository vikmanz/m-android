package com.vikmanz.shpppro.ui

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.vikmanz.shpppro.R
import com.vikmanz.shpppro.databinding.ActivityMainBinding
import com.vikmanz.shpppro.navigator.MainNavigator
import com.vikmanz.shpppro.ui.profile.MyProfileFragment
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

        // Init activity.
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)

        startRootFragment(savedInstanceState)
        supportFragmentManager.registerFragmentLifecycleCallbacks(fragmentCallbacks, false)
    }

    private fun startRootFragment(savedInstanceState: Bundle?) {
        log("if not null...")
        if (savedInstanceState == null) {
            log("create custom args")
            //val args = MyProfileFragment.CustomArgs()
            val args = MyProfileFragment.CustomArgs(getString(R.string.main_activity_person_email_hardcoded))
            log("start root fragment")
            navigator.launchMyProfile(args)
        }
//        if (savedInstanceState == null) {
//            val fragment = ProfileFragment.getInstance(
//                userEmail = intent.getStringExtra(INTENT_EMAIL_ID) ?: getString(R.string.main_activity_person_email_hardcoded)
//            )
//            supportFragmentManager
//                .beginTransaction()
//                .add(R.id.fragment_container_main_container, fragment)
//                .commit()
//        }
    }


    override fun onDestroy() {
        super.onDestroy()
        supportFragmentManager.unregisterFragmentLifecycleCallbacks(fragmentCallbacks)
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

    private val fragmentCallbacks = object : FragmentManager.FragmentLifecycleCallbacks() {
        override fun onFragmentViewCreated(fm: FragmentManager, f: Fragment, v: View, savedInstanceState: Bundle?) {
            if (supportFragmentManager.backStackEntryCount > 0) {
                // more than 1 screen -> show back button in the toolbar
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
            } else {
                supportActionBar?.setDisplayHomeAsUpEnabled(false)
            }

//            val result = navigator.result.value?.getValue() ?: return
//            if (f is BaseFragment) {
//                // has result that can be delivered to the screen's view-model
//                f.viewModel.onResult(result)
//            }
        }
    }

}

