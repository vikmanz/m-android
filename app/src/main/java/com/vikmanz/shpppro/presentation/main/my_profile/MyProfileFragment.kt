package com.vikmanz.shpppro.presentation.main.my_profile

import android.content.Intent
import androidx.fragment.app.viewModels
import com.vikmanz.shpppro.R
import com.vikmanz.shpppro.data.utils.EmailParser
import com.vikmanz.shpppro.databinding.FragmentMyProfileBinding
import com.vikmanz.shpppro.presentation.auth.AuthActivity
import com.vikmanz.shpppro.presentation.base.BaseFragment
import com.vikmanz.shpppro.presentation.utils.extensions.setImageWithGlide


class MyProfileFragment :
    BaseFragment<FragmentMyProfileBinding>(FragmentMyProfileBinding::inflate) {


    private val viewModel by viewModels<MyProfileViewModel>()

    override fun onCreatedFragmentView() {
        // Parse email, set Name Surname text and img of avatar.
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
        val emailToParse = viewModel.userEmail //requireArguments().getString(EMAIL_KEY)
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
        startAuthActivity()
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