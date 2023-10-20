package com.vikmanz.shpppro.presentation.screens.main.main_fragment.my_profile

import android.content.Intent
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.vikmanz.shpppro.R
import com.vikmanz.shpppro.databinding.FragmentMyProfileBinding
import com.vikmanz.shpppro.presentation.base.BaseFragment
import com.vikmanz.shpppro.presentation.screens.auth.AuthActivity
import com.vikmanz.shpppro.presentation.screens.main.main_fragment.MainViewPagerFragment
import com.vikmanz.shpppro.presentation.utils.extensions.setImageWithGlide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MyProfileFragment :
    BaseFragment<FragmentMyProfileBinding, MyProfileViewModel>(FragmentMyProfileBinding::inflate) {

    override val viewModel: MyProfileViewModel by viewModels()

    override fun setObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.user.collect { user ->
                    with(binding) {
                        textViewMyProfilePersonName.text = user.name ?: getString(R.string.main_profile_email_not_exist)
                        textViewMyProfilePersonCareer.text = user.career
                        textViewMyProfilePersonAddress.text = user.address
                    }
                }
            }
        }
    }


    override fun onReady() {
        setAvatar()
    }

    override fun setListeners() {
        with(binding) {
            textViewMyProfileLogoutButton.setOnClickListener { logout() }
            buttonMyProfileViewMyContacts.setOnClickListener { goToMyContacts() }
        }
    }

    /**
     * Set avatar image.
     */
    private fun setAvatar() =
        binding.imageViewMyProfileAvatarImage.setImageWithGlide(R.drawable.jpg_sample_avatar)

    /**
     * Logout with clear information about user from Data Store.
     */
    private fun logout() {
        viewModel.onLogout()
        startAuthActivity()             //TODO migrate to nav
    }

    /**
     * Finish that activity and start SignIn/SignUp activity.
     */
    //todo
    private fun startAuthActivity() {
        val intentObject = Intent(context, AuthActivity::class.java)
        startActivity(intentObject)
        activity?.overridePendingTransition(R.anim.zoom_in_inner, R.anim.zoom_in_outter)
        activity?.finish()
    }

    /**
     * Start My contacts activity.
     */
    //todo would be better to have an enum
    private fun goToMyContacts() {
        (parentFragment as MainViewPagerFragment).viewPager.currentItem = 1
    }

}