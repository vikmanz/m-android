package com.vikmanz.shpppro.ui.main.my_profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.vikmanz.shpppro.R
import com.vikmanz.shpppro.base.BaseFragmentWithArgs
import com.vikmanz.shpppro.data.utils.EmailParser
import com.vikmanz.shpppro.databinding.FragmentMyProfileBinding
import com.vikmanz.shpppro.ui.auth.AuthActivity
import com.vikmanz.shpppro.ui.utils.extensions.setImageWithGlide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyProfileFragment :
    BaseFragmentWithArgs<FragmentMyProfileBinding, MyProfileViewModel, MyProfileFragmentArgs>(FragmentMyProfileBinding::inflate) {

    override val args: MyProfileFragmentArgs by navArgs()
    override val viewModel: MyProfileViewModel by viewModels()

    override fun onReady(savedInstanceState: Bundle?) {
        super.onReady(savedInstanceState)
        setUserInformation()
        setAvatar()
    }

    override fun setListeners() {
        with(binding) {
            textViewMyProfileLogoutButton.setOnClickListener { logout() }
            buttonMyProfileViewMyContacts.setOnClickListener { goToMyContacts() }
        }
    }

    /**
     * Get full email, parse it and set name/surname of user.
     */
    private fun setUserInformation() {
        val emailToParse = viewModel.userEmail
        with(binding) {
            textViewMyProfilePersonName.text =
                if (emailToParse.isEmpty()) getString(R.string.main_activity_person_name_hardcoded)
                else EmailParser.getParsedNameSurname(emailToParse)
            textViewMyProfilePersonCareer.text =
                getString(R.string.main_activity_person_career_hardcoded)
            textViewMyProfilePersonAddress.text =
                getString(R.string.main_activity_person_address_hardcoded)
        }
    }

    /**
     * Set avatar image.
     */
    private fun setAvatar() =
        binding.imageViewMyProfileAvatarImage.setImageWithGlide(R.drawable.sample_avatar)

    /**
     * Logout with clear information about user from Data Store.
     */
    private fun logout() {
        viewModel.clearSavedUserData()
        startAuthActivity()             //TODO migrate to nav
    }

    /**
     * Finish that activity and start SignIn/SignUp activity.
     */
    private fun startAuthActivity() {
        val activity = requireActivity()
        val intentObject = Intent(activity, AuthActivity::class.java)
        startActivity(intentObject)
        activity.overridePendingTransition(R.anim.zoom_in_inner, R.anim.zoom_in_outter)
        activity.finish()
    }

    /**
     * Start My contacts activity.
     */
    private fun goToMyContacts() {
        viewModel.onMyContactsPressed()
    }

}