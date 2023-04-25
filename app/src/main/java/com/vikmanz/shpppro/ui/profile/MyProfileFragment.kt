package com.vikmanz.shpppro.ui.profile

import android.os.Bundle
import com.example.fragmentsnavigatortest.screens.base.BaseArgs
import com.example.fragmentsnavigatortest.screens.edit.MyProfileViewModel
import com.vikmanz.shpppro.R
import com.vikmanz.shpppro.data.EmailParser
import com.vikmanz.shpppro.databinding.FragmentMyProfileBinding
import com.vikmanz.shpppro.ui.base.BaseFragment
import com.vikmanz.shpppro.ui.base.screenViewModel
import com.vikmanz.shpppro.utilits.log

class MyProfileFragment : BaseFragment<FragmentMyProfileBinding, MyProfileViewModel>(FragmentMyProfileBinding::inflate) {

    class CustomArgs(userEmail: String) : BaseArgs {
        override val name = "profile"
        val emailToParse = userEmail
    }

    /**
     * Create ViewModel for this activity.
     */
    override val viewModel by screenViewModel()

    override fun onReady(savedInstanceState: Bundle?) {
        // nothing
    }

        /**
     * Main function, which used when activity was create.
     */
    override fun onCreate(savedInstanceState: Bundle?) {

        // Init activity.
        super.onCreate(savedInstanceState)
        log("create mpf")
        // Create Data Store.
        //loginData = DataStoreManager(requireContext())
    }

    override fun setStartUI() {
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
       // val emailToParse =  if (arguments == null) null else requireArguments().getString(EMAIL_KEY)
        val emailToParse = viewModel.userEmail //requireArguments().getString(EMAIL_KEY)
        with(binding) {
            textviewMainPersonName.text =
                if (emailToParse.isNullOrEmpty()) getString(R.string.main_activity_person_name_hardcoded)
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

    companion object {

        private const val EMAIL_KEY = "USER_EMAIL"
//        private const val COUNT_KEY = "COUNT_OF_FRAGMENTS"

        fun getInstance(userEmail: String): MyProfileFragment {
            val args: Bundle = Bundle().apply {
                putString(EMAIL_KEY, userEmail)
//                putInt(COUNT_KEY, count)
            }
            val fragment = MyProfileFragment()
            fragment.arguments = args
            return fragment
        }
    }

}