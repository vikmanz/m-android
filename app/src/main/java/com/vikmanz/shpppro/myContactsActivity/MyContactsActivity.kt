package com.vikmanz.shpppro.myContactsActivity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.vikmanz.shpppro.R
import com.vikmanz.shpppro.authActivity.AuthActivity
import com.vikmanz.shpppro.constants.Constants
import com.vikmanz.shpppro.constants.Constants.MARGINS_OF_ELEMENTS
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
                deleteContactFromViewModel(contact)
            }
        })
    }

    /**
     * Main function, which used when activity was create.
     */
    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyContactsBinding.inflate(layoutInflater).also { setContentView(it.root) }
        with(binding) {
            btnBack.setOnClickListener { startAuthActivity() }
            tvAddContacts.setOnClickListener { addNewContact() }
            tvAddContactsFromPhonebook.setOnClickListener {
                viewModel.getContactsFromPhonebook()
            }
        }
        initRecyclerView()
        setObserver()
    }


    private fun initRecyclerView() {
        with(binding) {
            recyclerViewMyContacts.layoutManager = LinearLayoutManager(this@MyContactsActivity)
            recyclerViewMyContacts.addItemDecoration(MarginItemDecoration(MARGINS_OF_ELEMENTS))
            recyclerViewMyContacts.adapter = adapter
        }
        initSwipeToDelete()
    }

    private fun initSwipeToDelete() {
        val swipeHandler = object : SwipeToDeleteCallback(this) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                deleteContactFromViewModel(viewModel.getContact(position))
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(binding.recyclerViewMyContacts)
    }

    private fun deleteContactFromViewModel(contact: Contact) {
        val position = viewModel.getContactPosition(contact)
        viewModel.deleteContact(contact)
        Snackbar
            .make(binding.root, "Contact has been removed", SNACK_BAR_VIEW_TIME)
            .setAction("Undo") { viewModel.addContact(contact, position) }
            .show()
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
        //return
        viewModel.addContact(contact = ContactsService().getOneContact())
    }
}