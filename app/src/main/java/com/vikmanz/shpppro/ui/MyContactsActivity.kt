package com.vikmanz.shpppro.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.vikmanz.shpppro.constants.Constants.MARGINS_OF_ELEMENTS
import com.vikmanz.shpppro.constants.Constants.SNACK_BAR_VIEW_TIME
import com.vikmanz.shpppro.databinding.ActivityMyContactsBinding
import com.vikmanz.shpppro.utilits.BaseActivity
import android.Manifest.permission.READ_CONTACTS
import com.vikmanz.shpppro.data.contactModel.ContactsService
import com.vikmanz.shpppro.data.MyContactsViewModel
import com.vikmanz.shpppro.data.contactModel.*
import com.vikmanz.shpppro.utilits.MarginItemDecoration
import com.vikmanz.shpppro.utilits.SwipeToDeleteCallback
import kotlinx.coroutines.*

/**
 * Class represents MyContacts screen activity.
 */
class MyContactsActivity :
    BaseActivity<ActivityMyContactsBinding>(ActivityMyContactsBinding::inflate),
    AddContactDialogFragment.ConfirmationListener {

    /**
     * Create ViewModel for this activity.
     */
    private val viewModel: MyContactsViewModel by viewModels()

    /**
     * Create service for create new contacts. It sends to Add new contact Dialog Fragment.
     */
    private val contactsService = ContactsService()

    /**
     * Main function, which used when activity was create.
     */
    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initRecyclerView()
        configureUI()
    }

    /**
     * Init recycler view and swipe to delete.
     */
    private fun initRecyclerView() {
        with(binding) {
            recyclerViewMyContacts.layoutManager = LinearLayoutManager(this@MyContactsActivity)
            recyclerViewMyContacts.addItemDecoration(MarginItemDecoration(MARGINS_OF_ELEMENTS))
            recyclerViewMyContacts.adapter = adapter
        }
        initSwipeToDelete()
    }

    /**
     * Create adapter for contacts recycler view.
     */
    private val adapter: ContactsAdapter by lazy {
        ContactsAdapter(contactActionListener = object : ContactActionListener {
            override fun onDeleteUser(contact: Contact) {
                // take contact from ContactsAdapter and delete it from ViewModel.
                deleteContactFromViewModelWithUndo(contact)
            }
        })
    }

    /**
     * Set observer for ViewModel. When ViewModel was changed, adapter of recycler view was take notify.
     */
    override fun setObservers() {
        lifecycleScope.launch {
            viewModel.contactList.collect { contactList ->
                adapter.submitList(contactList)
            }
        }
    }

    /**
     * Delete contact from ViewModel and show Undo to restore it.
     */
    private fun deleteContactFromViewModelWithUndo(contact: Contact) {
        val position = viewModel.getContactPosition(contact)
        viewModel.deleteContact(contact)
        Snackbar
            .make(binding.root, "Contact has been removed", SNACK_BAR_VIEW_TIME)
            .setAction("Undo") { viewModel.addContact(contact, position) }
            .show()
    }

    /**
     * Init swipe to delete. Taken from internet and didn't changed.
     */
    private fun initSwipeToDelete() {
        val swipeHandler = object : SwipeToDeleteCallback(this) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                deleteContactFromViewModelWithUndo(viewModel.getContact(position))
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(binding.recyclerViewMyContacts)
    }

    /**
     * Set listeners for buttons.
     */
    override fun setListeners() {
        with(binding) {
            btnBack.setOnClickListener { finish() }
            btnDeclineAccess.setOnClickListener { buttonToRemoveAccess() }
            tvAddContacts.setOnClickListener { addNewContact() }
            tvAddContactsFromPhonebook.setOnClickListener { requestReadContactsPermission() }
            tvAddContactsFromViewModel.setOnClickListener { changeToFakeContacts() }
        }
    }

    /**
     * Show Add new contact Dialog Fragment.
     */
    private fun addNewContact() {
        AddContactDialogFragment(contactsService)
            .show(supportFragmentManager, "ConfirmationDialogFragmentTag")
    }

    /**
     * Take Contact from Add new contact Dialog Fragment and add it to ViewModel.
     */
    override fun addContactConfirmButtonClicked(contact: Contact) {
        viewModel.addContact(contact)
    }

    /**
     * Start activity with request permission for read contacts from phonebook.
     */
    private fun requestReadContactsPermission() {
        requestPermissionLauncher.launch(READ_CONTACTS)
    }

    /**
     * Register activity for request permission for read contacts from phonebook.
     */
    private val requestPermissionLauncher =                         // req permissions
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it) setContactsFromPhone()                          // if yes set phone contacts
            else OnDeclinePermissionDialogFragment()                // if no show warning
                .show(supportFragmentManager, "ConfirmationDialogFragmentTag")
        }

    /**
     * Set contacts from phonebook which taken from ContactsPhoneInfoTaker to ViewModel list of contacts.
     */
    private fun setContactsFromPhone() {
        val phonebookInfo = ContactsPhoneInfoTaker(contentResolver).getPhonebookContactsInfo()
        val contactsFromPhone = ContactsService().createContactListFromPhonebookInfo(phonebookInfo)
        viewModel.setPhoneContactList(contactsFromPhone)
        configureUI()
    }

    /**
     * Button to open application settings to change permission.
     */
    private fun buttonToRemoveAccess() {
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

    /**
     * Change contacts to fake contacts list.
     */
    private fun changeToFakeContacts() {
        viewModel.getFakeContacts()
        configureUI()
    }

    /**
     * Configure UI to relevant buttons for phonebook or for fake contacts list.
     */
    private fun configureUI() {
        with(binding) {
            btnDeclineAccess.visibility =
                if (viewModel.phoneListActivated) View.VISIBLE else View.INVISIBLE

            if (viewModel.phoneListChangedToFake) {
                tvAddContactsFromViewModel.visibility = View.GONE
                tvAddContactsFromPhonebook.visibility = View.VISIBLE
            }
            else {
                tvAddContactsFromViewModel.visibility = View.VISIBLE
                tvAddContactsFromPhonebook.visibility = View.GONE
            }
        }
    }

    /**
     * Configure UI if activity restart (we don't go to onCreate()).
     */
    override fun onRestart() {
        super.onRestart()
        configureUI()
    }
}