package com.vikmanz.shpppro.ui.my_contacts

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.vikmanz.shpppro.constants.Constants.MARGINS_OF_ELEMENTS
import com.vikmanz.shpppro.constants.Constants.SNACK_BAR_VIEW_TIME
import android.Manifest.permission.READ_CONTACTS
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.vikmanz.shpppro.ui.base.BaseArgument
import com.vikmanz.shpppro.R
import com.vikmanz.shpppro.data.contactModel.*
import com.vikmanz.shpppro.databinding.FragmentMyContactsBinding
import com.vikmanz.shpppro.ui.base.BaseFragment
import com.vikmanz.shpppro.ui.base.screenViewModel
import com.vikmanz.shpppro.ui.my_contacts.addcontact.AddContactDialogFragment
import com.vikmanz.shpppro.utilits.MarginItemDecoration
import com.vikmanz.shpppro.utilits.SwipeToDeleteCallback
import com.vikmanz.shpppro.utilits.log
import kotlinx.coroutines.*

/**
 * Class represents MyContacts screen activity.
 */
class MyContactsFragment() : BaseFragment<FragmentMyContactsBinding, MyContactsViewModel>(FragmentMyContactsBinding::inflate) {

    // this screen accepts a string value from the HelloFragment
    class CustomArgument(
        override val name: String,
    ) : BaseArgument

    /**
     * Create ViewModel for this activity.
     */
    override val viewModel by screenViewModel()

    private var undo: Snackbar? = null

    /**
     * Set listeners for buttons.
     */
    override fun setListeners() {
        with(binding) {
            buttonMycontactsBack.setOnClickListener { viewModel.onButtonBackPressed() }
            buttonMycontactsDeclineAccess.setOnClickListener { buttonToRemoveAccess() }
            buttonMycontactsAddContact.setOnClickListener { addNewContact() }
            buttonMycontactsAddContactsFromPhonebook.setOnClickListener { requestReadContactsPermission() }
            buttonMycontactsAddContactsFromFaker.setOnClickListener { changeToFakeContacts() }
        }
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

    override fun setStartUi() {
        log("my contacts set UI!")
        initRecyclerView()
        updateUI()
    }

    /**
     * Init recycler view and swipe to delete.
     */
    private fun initRecyclerView() {
        with(binding) {
            recyclerviewMycontactsContactList.layoutManager =
                LinearLayoutManager(requireContext())
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
            override fun onTapUser(contactID: Long) {
                viewModel.onContactPressed(contactID)
            }

            override fun onDeleteUser(contact: Contact) {
                // take contact from ContactsAdapter and delete it from ViewModel.
                deleteContactFromViewModelWithUndo(contact)
            }
        })
    }



    /**
     * Delete contact from ViewModel and show Undo to restore it.
     */
    private fun deleteContactFromViewModelWithUndo(contact: Contact) {
        val position = viewModel.getContactPosition(contact)
        val isFakeData = viewModel.phoneListChangedToFake
        viewModel.deleteContact(contact)
        undo = Snackbar
            .make(binding.root, getString(R.string.my_contacts_remove_contact), SNACK_BAR_VIEW_TIME)
            .setAction(getString(R.string.my_contacts_remove_contact_undo)) {
                if (!viewModel.isContainsContact(contact) && viewModel.phoneListChangedToFake == isFakeData) {
                    viewModel.addContactToPosition(
                        contact,
                        position
                    )
                }
            }
        undo?.show()
    }

    /**
     * Init swipe to delete. Taken from internet and didn't changed.
     */
    private fun initSwipeToDelete() {
        val swipeHandler = object : SwipeToDeleteCallback(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                deleteContactFromViewModelWithUndo(viewModel.getContact(position))
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(binding.recyclerviewMycontactsContactList)
    }



    /**
     * Show Add new contact Dialog Fragment.
     */
    private fun addNewContact() {
        AddContactDialogFragment()
            .show(parentFragmentManager, "ConfirmationDialogFragmentTag")
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
                .show(parentFragmentManager, "ConfirmationDialogFragmentTag")
        }

    /**
     * Set contacts to phonebook.
     */
    private fun setContactsFromPhone() {
        viewModel.setPhoneContactList()
        undo?.dismiss()
        updateUI()
    }

    /**
     * Button to open application settings to change permission.
     */
    private fun buttonToRemoveAccess() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        with(intent) {
            data = Uri.fromParts("package", requireContext().packageName, null)
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
        undo?.dismiss()
        updateUI()
    }

    /**
     * Configure UI to relevant buttons for phonebook or for fake contacts list.
     */
    private fun updateUI() {
        with(binding) {
            if (viewModel.phoneListChangedToFake) {
                buttonMycontactsAddContactsFromFaker.visibility = View.GONE
                buttonMycontactsDeclineAccess.visibility = View.INVISIBLE
                textviewMycontactsRevokePermission.visibility = View.INVISIBLE
                buttonMycontactsAddContactsFromPhonebook.visibility = View.VISIBLE
            } else {
                buttonMycontactsAddContactsFromFaker.visibility = View.VISIBLE
                buttonMycontactsAddContactsFromPhonebook.visibility = View.GONE
                if (viewModel.phoneListActivated) {
                    buttonMycontactsDeclineAccess.visibility = View.VISIBLE
                    textviewMycontactsRevokePermission.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        undo = null
    }

}