package com.vikmanz.shpppro.presentation.screens.main.add_contact.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.vikmanz.shpppro.data.model.contact_item.AddContactItem
import com.vikmanz.shpppro.data.utils.contacts.SearchFilter
import com.vikmanz.shpppro.databinding.AddContactListItemBinding
import com.vikmanz.shpppro.presentation.screens.main.add_contact.adapter.diffutil.DiffUtilAddContactListItemComparator
import com.vikmanz.shpppro.presentation.utils.extensions.alsoLog
import com.vikmanz.shpppro.presentation.utils.extensions.setImageWithGlide
import com.vikmanz.shpppro.presentation.utils.extensions.setVisibleOrGone

/**
 * Adapter for Recycler view.
 */
class ContactsAdapter(
) : ListAdapter<AddContactItem, ContactsAdapter.AddContactViewHolder>(
    DiffUtilAddContactListItemComparator()
) {



    private var _preFilteredList: List<AddContactItem>? = mutableListOf()
    private val preFilteredList: List<AddContactItem>
        get() = _preFilteredList?.toList() ?: emptyList()


    fun filter(query: String): Boolean {
        @Suppress("UNCHECKED_CAST")
        val filtered =  SearchFilter.filter(
            list = preFilteredList,
            query = query
        ) as List<AddContactItem>
        submitList(filtered)
        return filtered.isEmpty()
    }

    fun submitListFromViewModel(list: List<AddContactItem>?){
        submitList(list)
        _preFilteredList = list
    }

    /**
     * Create one element from holder and return it.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddContactViewHolder {
        val binding =
            AddContactListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return AddContactViewHolder(binding)
    }


    /**
     * Bind info to one element holder.
     */
    override fun onBindViewHolder(holder: AddContactViewHolder, position: Int) {
        holder.bind(currentList[position])
    }


    inner class AddContactViewHolder(
        private val binding: AddContactListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(contactItem: AddContactItem) {
            with(binding) {
                with(contactItem) {

                    // Visibility of button
                    val isAddVisible = !isAdded && !isLoading && !isError
                    buttonAddContactAdd.setVisibleOrGone(isAddVisible)
                    textViewAddContactText.setVisibleOrGone(isAddVisible)
                    buttonAddContactLoading.setVisibleOrGone(isLoading)
                    buttonAddContactError.setVisibleOrGone(isError)
                    buttonAddContactDone.setVisibleOrGone(isAdded)

                    // Bind info
                    imageViewOneContactAvatarImage.setImageWithGlide(contact.image)
                    textViewOneContactName.text = contact.email
                    textViewOneContactCareer.text = contact.name

                    // Bind onClick listeners
                    buttonAddContactAdd.setOnClickListener {
                        onPlusClick(contactItem) alsoLog "click on plus"
                    }
                    buttonAddContactError.setOnClickListener {
                        onPlusClick(contactItem) alsoLog "click on error plus"
                    }
                    root.setOnClickListener { onClick(contactItem) alsoLog "click on item" }
                }
            }
        }
    }
}