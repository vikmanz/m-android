package com.vikmanz.shpppro.presentation.my_profile

import com.vikmanz.shpppro.presentation.base.BaseArgument
import com.vikmanz.shpppro.R
import com.vikmanz.shpppro.data.utils.EmailParser
import com.vikmanz.shpppro.databinding.FragmentMyProfileBinding
import com.vikmanz.shpppro.presentation.base.BaseFragment
import com.vikmanz.shpppro.presentation.utils.screenViewModel
import com.vikmanz.shpppro.utilits.extensions.log

class MyProfileFragment :
    BaseFragment<FragmentMyProfileBinding, MyProfileViewModel>(FragmentMyProfileBinding::inflate) {

    class CustomArgument(
        override val name: String,
        val email: String
    ) : BaseArgument



    /**
     * Create ViewModel for this activity.
     */
    override val viewModel by screenViewModel()

    //val loginData = DataStoreManager(requireContext()) //in onCreate

    override fun setStartUi() {
        // Parse email, set Name Surname text and img of avatar.
        log("setted starter ui")
        setUserInformation()
        setAvatar()
    }

    override fun setObservers() {
        return
    }

    override fun setListeners() {
        log("set listener")
        // binding.textviewMainLogoutButton.setOnClickListener { logout() }
        binding.buttonMainViewMyContacts.setOnClickListener { goToMyContacts() }
    }

    /**
     * Get full email, parse it and set name/surname of user.
     */
    private fun setUserInformation() {
        val emailToParse = viewModel.userEmail //requireArguments().getString(EMAIL_KEY)
        with(binding) {
            textviewMainPersonName.text =
                if (emailToParse.isEmpty()) getString(R.string.main_activity_person_name_hardcoded)
                else EmailParser().getParsedNameSurname(emailToParse)
            textviewMainPersonCareer.text =
                getString(R.string.main_activity_person_career_hardcoded)
            textviewMainPersonAddress.text =
                getString(R.string.main_activity_person_address_hardcoded)
        }
    }

    /**
     * Set avatar image.
     */
    private fun setAvatar() =
        binding.imageviewMainAvatarImage.setImageResource(R.drawable.sample_avatar)

    /**
     * Logout with clear information about user from Data Store.
     */
//    private fun logout() {
//        coroutineScope.launch(Dispatchers.IO) { loginData.clearUser() }
//        finishActivity()
//    }

    /**
     * Finish that activity and start SignIn/SignUp activity.
     */
//    private fun finishActivity() {
//        val intentObject = Intent(this, AuthActivity::class.java)
//        finish()
//        startActivity(intentObject)
//        overridePendingTransition(R.anim.zoom_out_inner, R.anim.zoom_out_outter)
//    }

    /**
     * Start My contacts activity.
     */
    private fun goToMyContacts() {
        viewModel.onMyContactsPressed()
        //        log("create new my contacts fragment")
//        val fragment = ContactsFragment.getInstance()
//        parentFragmentManager
//            .beginTransaction()
//            .addToBackStack(null)
//            .add(R.id.fragment_container_main_container, fragment)
//            .commit()
//        log("started new my contacts fragment")
    }
}