package com.vikmanz.shpppro.presentation.main.my_contacts_list

import android.Manifest.permission.READ_CONTACTS
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.vikmanz.shpppro.R
import com.vikmanz.shpppro.constants.Constants.MARGINS_OF_ELEMENTS
import com.vikmanz.shpppro.constants.Constants.SNACK_BAR_VIEW_TIME
import com.vikmanz.shpppro.data.contact_model.Contact
import com.vikmanz.shpppro.databinding.FragmentMyContactsListBinding
import com.vikmanz.shpppro.presentation.base.BaseArgument
import com.vikmanz.shpppro.presentation.base.BaseFragment
import com.vikmanz.shpppro.presentation.main.my_contacts_list.adapter.ContactsAdapter
import com.vikmanz.shpppro.presentation.main.my_contacts_list.adapter.listeners.ContactActionListener
import com.vikmanz.shpppro.presentation.main.my_contacts_list.add_contact.AddContactDialogFragment
import com.vikmanz.shpppro.presentation.main.my_contacts_list.decline_permision.OnDeclinePermissionDialogFragment
import com.vikmanz.shpppro.presentation.utils.extensions.setGone
import com.vikmanz.shpppro.presentation.utils.extensions.setMultipleInvisible
import com.vikmanz.shpppro.presentation.utils.extensions.setMultipleVisible
import com.vikmanz.shpppro.presentation.utils.extensions.setVisible
import com.vikmanz.shpppro.presentation.utils.recycler_view_decoration.MarginItemDecoration
import com.vikmanz.shpppro.presentation.utils.recycler_view_decoration.SwipeToDeleteCallback
import com.vikmanz.shpppro.presentation.utils.screenMainViewModel
import kotlinx.coroutines.launch

/**
 * Class represents MyContacts screen activity.
 */
class MyContactsListFragment :
    BaseFragment<FragmentMyContactsListBinding, MyContactsListViewModel>(FragmentMyContactsListBinding::inflate) {

    /**
     * Create ViewModel for this activity. Custom class need to change relevant type of viewModel in fabric.
     */
    class CustomArgument : BaseArgument
    override val viewModel by screenMainViewModel()

    private lateinit var uiObserver: Observer<Boolean>

    private var undo: Snackbar? = null

    private var deletedContact: Contact? = null

    /**
     * Set listeners for buttons.
     */
    override fun setListeners() {
        with(binding) {
            buttonMyContactsBackButton.setOnClickListener { viewModel.onButtonBackPressed() }
            buttonMyContactsDeclineAccess.setOnClickListener { buttonToRemoveAccess() }
            buttonMyContactsAddContact.setOnClickListener { addNewContact() }
            buttonMyContactsAddContactsFromPhonebook.setOnClickListener { requestReadContactsPermission() }
            buttonMyContactsAddContactsFromFaker.setOnClickListener { changeContactsList() }
        }
    }

    /**
     * Set observer for ViewModel. When ViewModel was changed, adapter of recycler view was take notify.
     */
    override fun setObservers() {
        observeContactsList()
        observeUI()
    }


    override fun onCreatedFragmentView() {
        initRecyclerView()
    }

    private fun observeUI() {
        uiObserver = Observer<Boolean> {
            with(binding) {
                if (it) {
                    setMultipleInvisible(
                        buttonMyContactsDeclineAccess,
                        textviewMyContactsRevokePermission
                    )
                    buttonMyContactsAddContactsFromFaker.setGone()
                    buttonMyContactsAddContactsFromPhonebook.setVisible()
                } else {
                    setMultipleVisible(
                        buttonMyContactsAddContactsFromFaker,
                        buttonMyContactsDeclineAccess,
                        textviewMyContactsRevokePermission
                    )
                    buttonMyContactsAddContactsFromPhonebook.setGone()
                }
            }
        }.also { viewModel.fakeListActivated.observe(this@MyContactsListFragment, it) }
    }

    private fun observeContactsList() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.contactList.collect { contactList ->
                    adapter.submitList(contactList)
                }
            }
        }
    }


    /**
     * Init recycler view and swipe to delete.
     */
    private fun initRecyclerView() {
        with(binding) {
            recyclerViewMyContactsContactList.layoutManager =
                LinearLayoutManager(requireContext())
            recyclerViewMyContactsContactList.addItemDecoration(
                MarginItemDecoration(
                    MARGINS_OF_ELEMENTS
                )
            )
            recyclerViewMyContactsContactList.adapter = adapter
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
               if (contact != deletedContact) deleteContactFromViewModelWithUndo(contact)
            }
        })
    }


    /**
     * Delete contact from ViewModel and show Undo to restore it.
     */
    private fun deleteContactFromViewModelWithUndo(contact: Contact) {
        deletedContact = contact
        val position = viewModel.getContactPosition(contact)
        viewModel.deleteContact(contact)
        undo = Snackbar
            .make(binding.root, getString(R.string.my_contacts_remove_contact), SNACK_BAR_VIEW_TIME)
            .setAction(getString(R.string.my_contacts_remove_contact_undo)) {
                if (!viewModel.isContainsContact(contact)) {
                    viewModel.addContactToPosition(
                        contact,
                        position
                    )
                    deletedContact = null
                    undo?.dismiss()
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
                viewModel.getContact(position)?.let { deleteContactFromViewModelWithUndo(it) }
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(binding.recyclerViewMyContactsContactList)
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
            if (it) changeContactsList()                          // if yes set phone contacts
            else OnDeclinePermissionDialogFragment()                // if no show warning
                .show(parentFragmentManager, "ConfirmationDialogFragmentTag")
        }

    /**
     * Set contacts to phonebook or back to fake list.
     */
    private fun changeContactsList() {
        viewModel.getContactsList()
        undo?.dismiss()
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


    override fun onDestroyView() {
        super.onDestroyView()
        deletedContact = null
        undo?.dismiss()
        undo = null
        viewModel.fakeListActivated.removeObserver(uiObserver)
    }

}