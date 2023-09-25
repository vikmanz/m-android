package com.vikmanz.shpppro.presentation.screens.main.add_contact

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.vikmanz.shpppro.common.Constants
import com.vikmanz.shpppro.databinding.FragmentAddContactMyContactsBinding
import com.vikmanz.shpppro.presentation.base.BaseFragment
import com.vikmanz.shpppro.presentation.screens.main.add_contact.adapter.ContactsAdapter
import com.vikmanz.shpppro.presentation.screens.main.main_fragment.my_contacts_list.adapter.decorator.MarginItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddContactFragment :
    BaseFragment<FragmentAddContactMyContactsBinding, AddContactViewModel>(
        FragmentAddContactMyContactsBinding::inflate
    ) {

    override val viewModel: AddContactViewModel by viewModels()


    /**
     * Create adapter for contacts recycler view.
     */
    private val adapterForRecycler = ContactsAdapter()


    /**
     * Set listeners for buttons.
     */
    override fun setListeners() {
        with(binding) {
            buttonMyContactsBackButton.setOnClickListener { viewModel.onButtonBackPressed() }
        }
    }


    /**
     * Set observer for ViewModel. When ViewModel was changed, adapter of recycler view was take notify.
     */
    override fun setObservers() {
        observeContactsList()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
    }

    private fun observeContactsList() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.contactList.collect { contactList ->
                    adapterForRecycler.submitList(contactList)
                }
            }
        }
    }


    /**
     * Init recycler view and swipe to delete.
     */
    private fun initRecyclerView() {
        with(binding.recyclerViewMyContactsContactList) {
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(MarginItemDecoration(Constants.MARGINS_OF_ELEMENTS))
            adapter = adapterForRecycler
        }
    }
}