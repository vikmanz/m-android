package com.vikmanz.shpppro.ui.main.main_fragment.my_contacts_list

import android.Manifest.permission.READ_CONTACTS
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.vikmanz.shpppro.R
import com.vikmanz.shpppro.base.BaseFragment
import com.vikmanz.shpppro.constants.Constants.MARGINS_OF_ELEMENTS
import com.vikmanz.shpppro.constants.Constants.SNACK_BAR_VIEW_TIME
import com.vikmanz.shpppro.data.contact_model.Contact
import com.vikmanz.shpppro.databinding.FragmentMyContactsListBinding
import com.vikmanz.shpppro.ui.main.main_fragment.MainViewPagerFragment
import com.vikmanz.shpppro.ui.main.main_fragment.my_contacts_list.adapter.ContactsAdapter
import com.vikmanz.shpppro.ui.main.main_fragment.my_contacts_list.adapter.listeners.ContactActionListener
import com.vikmanz.shpppro.ui.main.main_fragment.my_contacts_list.add_contact.AddContactDialogFragment
import com.vikmanz.shpppro.ui.main.main_fragment.my_contacts_list.decline_permision.OnDeclinePermissionDialogFragment
import com.vikmanz.shpppro.ui.utils.extensions.setGone
import com.vikmanz.shpppro.ui.utils.extensions.setMultipleInvisible
import com.vikmanz.shpppro.ui.utils.extensions.setMultipleVisible
import com.vikmanz.shpppro.ui.utils.extensions.setVisible
import com.vikmanz.shpppro.ui.utils.recycler_view_decoration.MarginItemDecoration
import com.vikmanz.shpppro.ui.utils.recycler_view_decoration.SwipeToDeleteCallback
import com.vikmanz.shpppro.utilits.extensions.isFalse
import com.vikmanz.shpppro.utilits.extensions.startDeclineAccessActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

private const val ADD_CONTACT_DIALOG_TAG = "ConfirmationDialogFragmentTag"

/**
 * Class represents MyContacts screen activity.
 */
@AndroidEntryPoint
class MyContactsListFragment :
    BaseFragment<FragmentMyContactsListBinding, MyContactsListViewModel>(
        FragmentMyContactsListBinding::inflate
    ) {

    override val viewModel: MyContactsListViewModel by viewModels()

    private var undo: Snackbar? = null

    /**
     * Create adapter for contacts recycler view.
     */
    private val adapter: ContactsAdapter by lazy {
        ContactsAdapter(contactActionListener = object : ContactActionListener {

            override fun onTapContact(contact: Contact) {
                viewModel.onContactPressed(contact)
            }

            override fun onLongTapContact(contact: Contact) {
                viewModel.swapSelectMode(contact)
            }

            override fun onDeleteContact(contact: Contact) {
                deleteContactWithUndo(contact)
            }

        })
    }

    /**
     * Register activity for request permission for read contacts from phonebook.
     */
    private val requestPermissionLauncher =                         // req permissions
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it) changeContactsList()                          // if yes set phone contacts
            else OnDeclinePermissionDialogFragment()                // if no show warning
                .show(parentFragmentManager, ADD_CONTACT_DIALOG_TAG)
        }

    /**
     * Set listeners for buttons.
     */
    override fun setListeners() {
        with(binding) {
            buttonMyContactsBackButton.setOnClickListener { onButtonBackPressed() }
            buttonMyContactsDeclineAccess.setOnClickListener { startDeclineAccessActivity() }
            buttonMyContactsAddContact.setOnClickListener { addNewContact() }
            buttonMyContactsAddContactsFromPhonebook.setOnClickListener { requestReadContactsPermission() }
            buttonMyContactsAddContactsFromFaker.setOnClickListener { changeContactsList() }
            buttonMyContactsDeleteMultipleContacts.setOnClickListener { deleteMultipleContacts() }
        }
    }

    private fun deleteMultipleContacts() {
        binding.buttonMyContactsDeleteMultipleContacts.setGone()
        viewModel.deleteMultipleContacts()
    }

    private fun onButtonBackPressed() {         //todo extension
        (parentFragment as MainViewPagerFragment).viewPager.currentItem = 0
    }

    /**
     * Set observer for ViewModel. When ViewModel was changed, adapter of recycler view was take notify.
     */
    override fun setObservers() {
        observeContactsList()
        observeUI()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
    }

    private fun observeUI() {
        viewModel.fakeListActivated.observe(viewLifecycleOwner) {
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
        }

        viewModel.isMultiselectMode.observe(viewLifecycleOwner) {
            adapter.setMultiselectMode(it)
            binding.recyclerViewMyContactsContactList.adapter = adapter     // adapter.notifyDataSetChanged()
            binding.buttonMyContactsDeleteMultipleContacts.apply {
                if (it) setVisible() else setGone()
            }
        }
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
            //recyclerViewMyContactsContactList.adapter = adapter
        }
        initSwipeToDelete()
    }


    private fun deleteContactWithUndo(contact: Contact) {
        if (viewModel.deleteContact(contact)) createUndo()
    }

    /**
     * Delete contact from ViewModel and show Undo to restore it.
     */
    private fun createUndo() {
        undo = Snackbar
            .make(binding.root, getString(R.string.my_contacts_remove_contact), SNACK_BAR_VIEW_TIME)
            .setAction(getString(R.string.my_contacts_remove_contact_undo)) {
                viewModel.restoreLastDeletedContact()
                undo?.dismiss()
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
                viewModel.getContact(position)?.let {
                    deleteContactWithUndo(it)
                }
            }

            override fun isItemViewSwipeEnabled(): Boolean {
                return viewModel.isMultiselectMode.isFalse()
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
            .show(parentFragmentManager, ADD_CONTACT_DIALOG_TAG)
    }

    /**
     * Start activity with request permission for read contacts from phonebook.
     */
    private fun requestReadContactsPermission() {
        requestPermissionLauncher.launch(READ_CONTACTS)
    }

    /**
     * Set contacts to phonebook or back to fake list.
     */
    private fun changeContactsList() {
        viewModel.changeContactList()
        undo?.dismiss()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        undo?.dismiss()
        undo = null
    }
}