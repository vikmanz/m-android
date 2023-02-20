package com.vikmanz.shpppro.myContactsActivity

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.vikmanz.shpppro.R
import com.vikmanz.shpppro.authActivity.AuthActivity
import com.vikmanz.shpppro.constants.Constants
import com.vikmanz.shpppro.constants.Constants.SNACK_BAR_VIEW_TIME
import com.vikmanz.shpppro.databinding.ActivityMyContactsBinding
import com.vikmanz.shpppro.myContactsActivity.contactModel.*
import kotlinx.coroutines.*

/**
 * Class represents MyContacts screen activity.
 */
class MyContactsActivity : AppCompatActivity() {

    // Binding, Data Store and Coroutine Scope variables.
    private lateinit var binding: ActivityMyContactsBinding

    // ініціалізуємо viewModel з використанням viewModels()
    private val viewModel: MyContactsViewModel by viewModels()

    // Recycler View variables.
    private val adapter: ContactsAdapter by lazy {
        ContactsAdapter(contactActionListener = object : ContactActionListener {
            override fun onDeleteUser(contact: Contact) {
                val position = viewModel.getContactPosition(contact)
                viewModel.deleteContact(contact)
                showSnackBar(contact, position)
            }

            private fun showSnackBar(contact: Contact, position: Int) {
                Snackbar
                    .make(binding.root, "сontact has been removed", SNACK_BAR_VIEW_TIME)
                    .setAction("Undo") { viewModel.addContact(contact, position) }
                    .show()
            }
        })
    }


    /**
     * Main function, which used when activity was create.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyContactsBinding.inflate(layoutInflater).also { setContentView(it.root) }

        with(binding) {
            btnBack.setOnClickListener { startAuthActivity() }
            tvAddContacts.setOnClickListener { addNewContact() }
        }

        initRecyclerView()
        setObserver()
    }


    private fun initRecyclerView() {
        with(binding) {
            recyclerViewMyContacts.layoutManager = LinearLayoutManager(this@MyContactsActivity)
            recyclerViewMyContacts.addItemDecoration(MarginItemDecoration(20))
            recyclerViewMyContacts.adapter = adapter
        }
    }

    private fun setObserver() {
        lifecycleScope.launch {
            viewModel.contactList.collect { contactList ->
                adapter.submitList(contactList)
            }
        }
    }

    private fun startAuthActivity() {
        val intentObject = Intent(this, AuthActivity::class.java)
        intentObject.putExtra(Constants.INTENT_LANG_ID, false)
        startActivity(intentObject)
        overridePendingTransition(R.anim.zoom_in_inner, R.anim.zoom_in_outter)
        finish()
    }

    private fun addNewContact() {
        val contactsService = ContactsService()
        viewModel.addContact(contact = contactsService.getOneContact())
    }
}