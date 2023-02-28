package com.vikmanz.shpppro.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.vikmanz.shpppro.R
import com.vikmanz.shpppro.constants.Constants
import com.vikmanz.shpppro.constants.Constants.MARGINS_OF_ELEMENTS
import com.vikmanz.shpppro.constants.Constants.SNACK_BAR_VIEW_TIME
import com.vikmanz.shpppro.databinding.ActivityMyContactsBinding
import com.vikmanz.shpppro.myContactsActivity.AddContactDialogFragment
import com.vikmanz.shpppro.myContactsActivity.MarginItemDecoration
import com.vikmanz.shpppro.myContactsActivity.MyContactsViewModel
import com.vikmanz.shpppro.myContactsActivity.SwipeToDeleteCallback
import com.vikmanz.shpppro.myContactsActivity.contactModel.*
import com.vikmanz.shpppro.utilits.BaseActivity
import kotlinx.coroutines.*


/**
 * Class represents MyContacts screen activity.
 */
class MyContactsActivity :
    BaseActivity<ActivityMyContactsBinding>(ActivityMyContactsBinding::inflate),
    AddContactDialogFragment.ConfirmationListener {

    // ініціалізуємо viewModel з використанням viewModels()
    private val viewModel: MyContactsViewModel by viewModels()
    private val contactsService = ContactsService()
    private var changeToPhoneContacts = false

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
        with(binding) {
            btnBack.setOnClickListener { startAuthActivity() }
            buttonGrandPermissions.setOnClickListener { buttonToGrant() }
            btnDeclineAccess.setOnClickListener { buttonToGrant() }
            tvAddContacts.setOnClickListener { addNewContact() }
            tvAddContactsFromPhonebook.setOnClickListener {
                changeToPhoneContacts = true
                getContactsFromPhone()
                btnDeclineAccess.visibility = View.VISIBLE
                tvAddContactsFromPhonebook.visibility = View.GONE
                tvAddContactsFromViewModel.visibility = View.VISIBLE
            }
            tvAddContactsFromViewModel.setOnClickListener {
                changeToPhoneContacts = false
                viewModel.getFakeContacts()
                btnDeclineAccess.visibility = View.INVISIBLE
                tvAddContactsFromViewModel.visibility = View.GONE
                tvAddContactsFromPhonebook.visibility = View.VISIBLE
            }
        }
        initRecyclerView()
        setObserver()
    }

    private fun getContactsFromPhone() {
        val contactsInfo = ContactsFromPhonebookInformationTaker(
            this@MyContactsActivity,
            contentResolver
        ).getContactsInfo()
        if (contactsInfo == null) {
            binding.buttonGrandPermissions.visibility = View.VISIBLE
            viewModel.clearContactList()
        } else {
            binding.buttonGrandPermissions.visibility = View.GONE
            binding.btnDeclineAccess.visibility = View.VISIBLE
            viewModel.getContactsFromPhonebook(contactsInfo)
        }
    }

    private fun buttonToGrant() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        with(intent) {
            data = Uri.fromParts("package", applicationContext.packageName, null)
            addCategory(Intent.CATEGORY_DEFAULT)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
            addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
        }
        startActivity(intent)
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

        AddContactDialogFragment(contactsService)
            .show(supportFragmentManager, "ConfirmationDialogFragmentTag")

        //return

    }

    override fun confirmButtonClicked(contact: Contact) {
        viewModel.addContact(contact)
    }

    override fun onRestart() {
        if (changeToPhoneContacts) getContactsFromPhone()
        super.onRestart()
    }

    /**
     * Save Instance State.
     */
    override fun onSaveInstanceState(outState: Bundle) {
        outState.putBoolean(PHONEBOOK_STATE_KEY, changeToPhoneContacts)
        super.onSaveInstanceState(outState)
    }

    /**
     * Load Instance State.
     */
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        changeToPhoneContacts = savedInstanceState.getBoolean(PHONEBOOK_STATE_KEY)
        if (changeToPhoneContacts) getContactsFromPhone()
    }

    /**
     * Constants.
     */
    companion object {
        private const val PHONEBOOK_STATE_KEY = "PHONEBOOK_STATE_KEY_CONTACTS_ACTIVITY"
    }

}