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
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.vikmanz.shpppro.App
import com.vikmanz.shpppro.R
import com.vikmanz.shpppro.data.contactModel.*
import com.vikmanz.shpppro.utilits.MarginItemDecoration
import com.vikmanz.shpppro.utilits.SwipeToDeleteCallback
import kotlinx.coroutines.*

/**
 * Class represents MyContacts screen activity.
 */
class MyContactsActivity :
    BaseActivity<ActivityMyContactsBinding>(ActivityMyContactsBinding::inflate)
    {
// ,AddContactDialogFragment.ConfirmationListener
    /**
     * Create service for create new contacts. It sends to Add new contact Dialog Fragment.
     */
    private val contactsService = App.contactsService

    /**
     * Create ViewModel for this activity.
     */
    private val viewModel: MyContactsViewModel by viewModels {
        MyContactsViewModelFactory(contactsService)
    }


    /**
     * Main function, which used when activity was create.
     */
    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initRecyclerView()
        updateUI()
    }

    /**
     * Init recycler view and swipe to delete.
     */
    private fun initRecyclerView() {
        with(binding) {
            recyclerviewMycontactsContactList.layoutManager =
                LinearLayoutManager(this@MyContactsActivity)
            recyclerviewMycontactsContactList.addItemDecoration(
                MarginItemDecoration(
                    MARGINS_OF_ELEMENTS
                )
            )
            recyclerviewMycontactsContactList.adapter = adapter
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
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.contactList.collect { contactList ->
                    adapter.submitList(contactList)
                }
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
            .make(binding.root, getString(R.string.my_contacts_remove_contact), SNACK_BAR_VIEW_TIME)
            .setAction(getString(R.string.my_contacts_remove_contact_undo)) {
                viewModel.addContactToPosition(
                    contact,
                    position
                )
            }
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
        itemTouchHelper.attachToRecyclerView(binding.recyclerviewMycontactsContactList)
    }

    /**
     * Set listeners for buttons.
     */
    override fun setListeners() {
        with(binding) {
            buttonMycontactsBack.setOnClickListener { finish() }
            buttonMycontactsDeclineAccess.setOnClickListener { buttonToRemoveAccess() }
            buttonMycontactsAddContact.setOnClickListener { addNewContact() }
            buttonMycontactsAddContactsFromPhonebook.setOnClickListener { requestReadContactsPermission() }
            buttonMycontactsAddContactsFromFaker.setOnClickListener { changeToFakeContacts() }
        }
    }

    /**
     * Show Add new contact Dialog Fragment.
     */
    private fun addNewContact() {
        AddContactDialogFragment()
            .show(supportFragmentManager, "ConfirmationDialogFragmentTag")
    }

//    /**
//     * Take Contact from Add new contact Dialog Fragment and add it to ViewModel.
//     */
//    override fun addContactConfirmButtonClicked(contact: Contact) {
//        viewModel.addContact(contact)
//    }

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
     * Set contacts to phonebook.
     */
    private fun setContactsFromPhone() {
        viewModel.setPhoneContactList()
        updateUI()
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
        updateUI()
    }

    /**
     * Configure UI to relevant buttons for phonebook or for fake contacts list.
     */
    private fun updateUI() {
        with(binding) {
            if (viewModel.phoneListActivated) {
                buttonMycontactsDeclineAccess.visibility = View.VISIBLE
                textviewMycontactsRevokePermission.visibility = View.VISIBLE
            } else {
                buttonMycontactsDeclineAccess.visibility = View.INVISIBLE
                textviewMycontactsRevokePermission.visibility = View.GONE
            }


            if (viewModel.phoneListChangedToFake) {
                buttonMycontactsAddContactsFromFaker.visibility = View.GONE
                buttonMycontactsAddContactsFromPhonebook.visibility = View.VISIBLE
            } else {
                buttonMycontactsAddContactsFromFaker.visibility = View.VISIBLE
                buttonMycontactsAddContactsFromPhonebook.visibility = View.GONE
            }
        }
    }

    /**
     * Configure UI if activity restart (we don't go to onCreate()).
     */
    override fun onRestart() {
        super.onRestart()
        updateUI()
    }
}