package com.vikmanz.shpppro.presentation.auth.splash_screen

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.vikmanz.shpppro.R
import com.vikmanz.shpppro.constants.Constants
import com.vikmanz.shpppro.data.DataStoreManager
import com.vikmanz.shpppro.presentation.base.BaseArgument
import com.vikmanz.shpppro.databinding.FragmentSplashScreenBinding
import com.vikmanz.shpppro.presentation.base.BaseFragment
import com.vikmanz.shpppro.presentation.main.MainActivity
import com.vikmanz.shpppro.presentation.utils.screenAuthViewModel
import com.vikmanz.shpppro.utilits.extensions.log
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
class SplashScreenFragment :
    BaseFragment<FragmentSplashScreenBinding, SplashScreenViewModel>(FragmentSplashScreenBinding::inflate) {

    class CustomArgument() : BaseArgument

    /**
     * Create ViewModel for this activity.
     */
    override val viewModel by screenAuthViewModel()

    // Data Store and Coroutine Scope variables.
    private lateinit var dataStore: DataStoreManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataStore = DataStoreManager(requireActivity())
    }

    override fun setObservers() {
        setAutoLoginObserver()
    }

    /**
     * Check if user already save login-password, and do autologin if it's need.
     */
    private fun setAutoLoginObserver() {
        lifecycleScope.launch {
            dataStore.userNameFlow.collect { name ->
                if (name.isBlank()) {
                    log("datastore is null is [$name]")
                    viewModel.goToLoginFragment()
                } else {
                    log("datastore is not null! is [$name]")
                    startMainActivity(name)
                }
            }
        }
    }


    /**
     * Start main activity.
     *
     * @param email User email as String.
     */
    // TODO you can use nav graph for activity and fragment navigation either
    private fun startMainActivity(email: String) {
        val activity = requireActivity()
        val intentObject = Intent(activity, MainActivity::class.java)
        intentObject.putExtra(Constants.INTENT_EMAIL_ID, email)
        startActivity(intentObject)
        activity.overridePendingTransition(R.anim.zoom_in_inner, R.anim.zoom_in_outter)
        activity.finish()
    }

}