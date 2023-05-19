package com.vikmanz.shpppro.ui

import android.content.Intent
import android.os.Bundle
import com.vikmanz.shpppro.R
import com.vikmanz.shpppro.constants.Constants.INTENT_EMAIL_ID
import com.vikmanz.shpppro.data.DataStoreManager
import com.vikmanz.shpppro.data.EmailParser
import com.vikmanz.shpppro.databinding.ActivityMainBinding
import com.vikmanz.shpppro.utilits.BaseActivity
import kotlinx.coroutines.*

/**
 * Class represents user main profile screen activity.
 */
class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    // Data Store and Coroutine Scope variables.
    private lateinit var loginData: DataStoreManager
    private val coroutineScope: CoroutineScope = CoroutineScope(Job())


    /**
     * Main function, which used when activity was create.
     */
    override fun onCreate(savedInstanceState: Bundle?) {

        // Init activity.
        super.onCreate(savedInstanceState)

        // Create Data Store.
        loginData = DataStoreManager(this)

        // Parse email, set Name Surname text and img of avatar.
        setUserInformation()
        setAvatar()
    }


    override fun setListeners() {
        super.setListeners()
        binding.textviewMainLogoutButton.setOnClickListener { logout() }
        binding.buttonMainViewMyContacts.setOnClickListener { startMyContactsActivity() }
    }

    /**
     * Get full email, parse it and set name/surname of user.
     */
    private fun setUserInformation() {
        val emailToParse = intent?.getStringExtra(INTENT_EMAIL_ID)
        with(binding) {
            textviewMainPersonName.text =
                if (emailToParse.isNullOrEmpty()) getString(R.string.main_activity_person_name_hardcoded)
                else EmailParser().getParsedNameSurname(emailToParse)
            textviewMainPersonCareer.text = getString(R.string.main_activity_person_career_hardcoded)
            textviewMainPersonAddress.text = getString(R.string.main_activity_person_address_hardcoded)
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
    private fun logout() {
        coroutineScope.launch(Dispatchers.IO) { loginData.clearUser() }
        finishActivity()
    }

    /**
     * Finish that activity and start SignIn/SignUp activity.
     */
    private fun finishActivity() {
        val intentObject = Intent(this, AuthActivity::class.java)
        finish()
        startActivity(intentObject)
        overridePendingTransition(R.anim.zoom_out_inner, R.anim.zoom_out_outter)
    }

    /**
     * Start My contacts activity.
     */
    private fun startMyContactsActivity() {
        val intentObject = Intent(this, MyContactsActivity::class.java)
        startActivity(intentObject)
        overridePendingTransition(R.anim.zoom_in_inner, R.anim.zoom_in_outter)
    }

}

