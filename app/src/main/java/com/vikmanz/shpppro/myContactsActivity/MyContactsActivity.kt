package com.vikmanz.shpppro.myContactsActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.vikmanz.shpppro.App
import com.vikmanz.shpppro.authActivity.AuthActivity
import com.vikmanz.shpppro.R
import com.vikmanz.shpppro.constants.Constants
import com.vikmanz.shpppro.databinding.ActivityMyContactsBinding
import com.vikmanz.shpppro.myContactsActivity.contactModel.ContactsTaker
import com.vikmanz.shpppro.myContactsActivity.contactsRecycler.*
import com.vikmanz.shpppro.myContactsActivity.contactModel.OneContactService

/**
 * Class represents MyContacts screen activity.
 */
class MyContactsActivity : AppCompatActivity() {

    // Binding, Data Store and Coroutine Scope variables.
    private lateinit var binding: ActivityMyContactsBinding

    // Recycler View variables.
    private val adapter = OneContactAdapter()
    private val oneContactService: OneContactService
        get() = (applicationContext as App).oneContactService


    /**
     * Main function, which used when activity was create.
     */
    override fun onCreate(savedInstanceState: Bundle?) {

        // Init activity.
        super.onCreate(savedInstanceState)

        // Others init operations.
        binding = ActivityMyContactsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Back button.
        binding.btnBack.setOnClickListener {
            startAuthActivity()
        }

        initRecyclerView()

        binding.tvAddContactsFromPhonebook.setOnClickListener {
           changeToPhonebook()
        }

    }

    private fun initRecyclerView() {
        with(binding) {
            recyclerViewMyContacts.layoutManager = LinearLayoutManager(this@MyContactsActivity)
            recyclerViewMyContacts.addItemDecoration(MarginItemDecoration(20))
            recyclerViewMyContacts.adapter = adapter
            adapter.contactList = oneContactService.getContacts()
        }
    }

    private fun startAuthActivity() {
        val intentObject = Intent(this, AuthActivity::class.java)
        intentObject.putExtra(Constants.INTENT_LANG_ID, false)
        startActivity(intentObject)
        overridePendingTransition(R.anim.zoom_in_inner, R.anim.zoom_in_outter)
        finish()
    }

    private fun changeToPhonebook() {

        val contentResolver = this@MyContactsActivity.contentResolver
        val contacts = ContactsTaker().getContacts(contentResolver)

        val (name, number) = contacts[0]
        Log.d("MyLog", "Name: $name, Number: $number")
    }

}