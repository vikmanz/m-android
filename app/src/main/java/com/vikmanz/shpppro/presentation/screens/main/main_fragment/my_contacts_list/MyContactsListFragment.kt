package com.vikmanz.shpppro.presentation.screens.main.main_fragment.my_contacts_list

import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.vikmanz.shpppro.R
import com.vikmanz.shpppro.common.Constants.MARGINS_OF_ELEMENTS
import com.vikmanz.shpppro.common.Constants.SNACK_BAR_VIEW_TIME
import com.vikmanz.shpppro.databinding.FragmentMyContactsListBinding
import com.vikmanz.shpppro.presentation.base.BaseFragment
import com.vikmanz.shpppro.presentation.screens.main.main_fragment.MainViewPagerFragment
import com.vikmanz.shpppro.presentation.screens.main.main_fragment.my_contacts_list.adapter.ContactsAdapter
import com.vikmanz.shpppro.presentation.screens.main.main_fragment.my_contacts_list.adapter.decorator.MarginItemDecoration
import com.vikmanz.shpppro.presentation.screens.main.main_fragment.my_contacts_list.adapter.decorator.SwipeToDeleteCallback
import com.vikmanz.shpppro.presentation.utils.extensions.setGone
import com.vikmanz.shpppro.presentation.utils.extensions.setKeyboardVisibility
import com.vikmanz.shpppro.presentation.utils.extensions.setVisibleOrGone
import com.vikmanz.shpppro.presentation.utils.extensions.startDeclineAccessActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.toList
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
    private val adapterForRecycler = ContactsAdapter()

    /**
     * Set listeners for buttons.
     */
    override fun setListeners() {
        with(binding) {
            buttonMyContactsBackButton.setOnClickListener { onButtonBackPressed() }
            buttonMyContactsAddContact.setOnClickListener { viewModel.startAddContact() }
            // buttonMyContactsAddContactsFromPhonebook.setOnClickListener { requestReadContactsPermission() }
            // buttonMyContactsAddContactsFromFaker.setOnClickListener { changeContactsList() }
            buttonMyContactsDeleteMultipleContacts.setOnClickListener { deleteMultipleContacts() }
            buttonMyContactsContactSearch.setOnClickListener { setSearchMode(true) }
            buttonMyContactCancelSearch.setOnClickListener { setSearchMode(false) }
            setSearchBarListeners()
        }
    }

    private fun deleteMultipleContacts() {
        binding.buttonMyContactsDeleteMultipleContacts.setGone()
        //viewModel.deleteMultipleContacts()
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
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    with(binding) {

                        // observe loading data
                        recyclerViewMyContactsContactList.setVisibleOrGone(!state.isLoadingData)
                        progressBarContactList.setVisibleOrGone(state.isLoadingData)

                        // observe multiselect mode
                        adapterForRecycler.isMultiselect = state.isMultiselectMode
                        binding.buttonMyContactsDeleteMultipleContacts.setVisibleOrGone(state.isMultiselectMode)

                        // observe Undo
                        if (state.isShowSnackBar) showUndo()

                        // observe search bar
                        val isSearchMode = state.isSearchMode
                        textViewMyContactsTitle.setVisibleOrGone(!isSearchMode)
                        buttonMyContactsContactSearch.setVisibleOrGone(!isSearchMode)
                        buttonMyContactsBackButton.setVisibleOrGone(!isSearchMode)
                        searchBar.setVisibleOrGone(isSearchMode)
                        buttonMyContactCancelSearch.setVisibleOrGone(isSearchMode)

                        //setNoContactsDisclaimerVisible(adapterForRecycler.currentList.isEmpty() && !state.isLoadingData)
                    }
                }
            }
        }
    }

    private fun setNoContactsDisclaimerVisible(isVisible: Boolean) {
        with(binding) {
            textViewNoResultsTitle.setVisibleOrGone(isVisible && !viewModel.uiState.value.isLoadingData)
            textViewNoResultsSubtitle.setVisibleOrGone(isVisible  && !viewModel.uiState.value.isLoadingData)
        }
    }


    private fun setSearchBarListeners() {
        with(binding) {
            searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(newText: String?): Boolean {
                    filterList(newText)
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    filterList(newText)
                    return false
                }

                fun filterList(newText: String?) {
                    setNoContactsDisclaimerVisible(adapterForRecycler.filter(newText ?: ""))
                }
            })
        }
    }

    private fun observeContactsList() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.contactList.collect { contactList ->
                    adapterForRecycler.submitListFromViewModel(contactList)
                    adapterForRecycler.filter(binding.searchBar.query.toString())
                    //setNoContactsDisclaimerVisible(adapterForRecycler.currentList.isEmpty())
                }
            }
        }
    }

    private fun setSearchMode(isSearchMode: Boolean) {
        viewModel.setSearchMode(isSearchMode)
        with(binding.searchBar) {
            setKeyboardVisibility(isSearchMode, this)
            this.setQuery("", true)    // clear search
        }
    }

    /**
     * Init recycler view and swipe to delete.
     */
    private fun initRecyclerView() {
        with(binding.recyclerViewMyContactsContactList) {
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(MarginItemDecoration(MARGINS_OF_ELEMENTS))
            adapter = adapterForRecycler
        }
        initSwipeToDelete()
    }

//    private fun deleteContactWithUndo(contact: ContactItem) {
//        if (viewModel.deleteContact(contact)) createUndo()
//    }

    /**
     * Delete contact from ViewModel and show Undo to restore it.
     */
    private fun showUndo() {
        undo = Snackbar
            .make(
                binding.root,
                getString(R.string.my_contacts_remove_contact),
                SNACK_BAR_VIEW_TIME.toInt()
            )
            .setAction(getString(R.string.my_contacts_remove_contact_undo)) {
                viewModel.restoreDeletedContact()
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
//                viewModel.getContact(position)?.let {
//                    deleteContactWithUndo(it)
//                }
            }

            override fun isItemViewSwipeEnabled(): Boolean {
                return !viewModel.uiState.value.isMultiselectMode
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(binding.recyclerViewMyContactsContactList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        undo?.dismiss()
        undo = null
    }
}