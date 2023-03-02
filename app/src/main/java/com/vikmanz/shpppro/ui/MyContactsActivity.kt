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
import com.vikmanz.shpppro.R
import com.vikmanz.shpppro.constants.Constants
import com.vikmanz.shpppro.constants.Constants.MARGINS_OF_ELEMENTS
import com.vikmanz.shpppro.constants.Constants.SNACK_BAR_VIEW_TIME
import com.vikmanz.shpppro.databinding.ActivityMyContactsBinding
import com.vikmanz.shpppro.myContactsActivity.contactModel.*
import com.vikmanz.shpppro.utilits.BaseActivity
import android.Manifest.permission.READ_CONTACTS
import com.vikmanz.shpppro.myContactsActivity.*
import com.vikmanz.shpppro.utilits.MarginItemDecoration
import com.vikmanz.shpppro.utilits.SwipeToDeleteCallback
import kotlinx.coroutines.*


/**
 * Class represents MyContacts screen activity.
 */
class MyContactsActivity :
    BaseActivity<ActivityMyContactsBinding>(ActivityMyContactsBinding::inflate),
    AddContactDialogFragment.ConfirmationListener {


    /// CREATE AND INIT ACTIVITY ///

    private val viewModel: MyContactsViewModel by viewModels()

    /**
     * Main function, which used when activity was create.
     */
    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initRecyclerView()
    }

    override fun setListeners() {
        with(binding) {
            btnBack.setOnClickListener { startAuthActivity() }
            btnDeclineAccess.setOnClickListener { buttonToRemoveAccess() }
            tvAddContacts.setOnClickListener { addNewContact() }
            tvAddContactsFromPhonebook.setOnClickListener { requestReadContactsPermission() }
            tvAddContactsFromViewModel.setOnClickListener { changeToFakeContacts() }
        }
    }

    override fun setObservers() {
        lifecycleScope.launch {
            viewModel.contactList.collect { contactList ->
                adapter.submitList(contactList)
            }
        }
    }



    /// RECYCLER VIEW ///

    private val contactsService = ContactsService()

    // Recycler View variables.
    private val adapter: ContactsAdapter by lazy {
        ContactsAdapter(contactActionListener = object : ContactActionListener {
            override fun onDeleteUser(contact: Contact) {
                deleteContactFromViewModelWithUndo(contact)
            }
        })
    }

    private fun initRecyclerView() {
        with(binding) {
            recyclerViewMyContacts.layoutManager = LinearLayoutManager(this@MyContactsActivity)
            recyclerViewMyContacts.addItemDecoration(MarginItemDecoration(MARGINS_OF_ELEMENTS))
            recyclerViewMyContacts.adapter = adapter
        }
        initSwipeToDelete()
    }

    private fun addNewContact() {
        AddContactDialogFragment(contactsService)
            .show(supportFragmentManager, "ConfirmationDialogFragmentTag")
    }

    override fun addContactConfirmButtonClicked(contact: Contact) {
        viewModel.addContact(contact)
    }

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

    private fun deleteContactFromViewModelWithUndo(contact: Contact) {
        val position = viewModel.getContactPosition(contact)
        viewModel.deleteContact(contact)
        Snackbar
            .make(binding.root, "Contact has been removed", SNACK_BAR_VIEW_TIME)
            .setAction("Undo") { viewModel.addContact(contact, position) }
            .show()
    }




    /// TAKE CONTACTS FROM PHONE ///

    private fun requestReadContactsPermission() {
        requestPermissionLauncher.launch(READ_CONTACTS)
    }

    private val requestPermissionLauncher =                         // req permissions
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it) setContactsFromPhone()                          // if yes set phone contacts
            else OnDeclinePermissionDialogFragment()                // if no show warning
                .show(supportFragmentManager, "ConfirmationDialogFragmentTag")
        }

    private fun setContactsFromPhone() {
        val phonebookInfo = ContactsPhoneInfoTaker(contentResolver).getPhonebookContactsInfo()
        val contactsFromPhone = ContactsService().createContactListFromPhonebookInfo(phonebookInfo)
        viewModel.setPhoneContactList(contactsFromPhone)
        configureUI()
    }

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
    private fun changeToFakeContacts() {
        viewModel.getFakeContacts()
        configureUI()
    }

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

    override fun onRestart() {
        configureUI()
        super.onRestart()
    }






    /// OTHERS FUNCTIONS ///
    private fun startAuthActivity() {
        val intentObject = Intent(this, AuthActivity::class.java)
        intentObject.putExtra(Constants.INTENT_LANG_ID, false)
        startActivity(intentObject)
        overridePendingTransition(R.anim.zoom_in_inner, R.anim.zoom_in_outter)
        finish()
    }

}