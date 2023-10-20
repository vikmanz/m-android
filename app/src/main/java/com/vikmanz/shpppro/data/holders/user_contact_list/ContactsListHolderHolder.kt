package com.vikmanz.shpppro.data.holders.user_contact_list

import com.vikmanz.shpppro.data.model.contact_item.ContactItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@Suppress("MemberVisibilityCanBePrivate")
object ContactsListHolderHolder : ShPPUserContactsListHolder {

    //This object is a wrapper. if we pass it a new object it will call emit
    private val _contactList = MutableStateFlow(listOf<ContactItem>())

    //this object sends out the immutable list
    override val contactList: StateFlow<List<ContactItem>> = _contactList.asStateFlow()

    private val multiselectIdList = mutableListOf<Int>()

    override fun findContact(contactId: Int): ContactItem {
        return contactList.value[contactId]
    }

    override fun updateContactList(
        newContactList: List<ContactItem>
    ) {
        _contactList.update { newContactList.sortedBy { it.contact.id } }
    }

    override fun changeContactItemCheckedState(contactItemId: Int): Boolean {
        if (multiselectIdList.any { it == contactItemId }) {
            multiselectIdList.remove(contactItemId)
            updateCheckedState(contactItemId, false)
        } else {
            multiselectIdList.add(contactItemId)
            updateCheckedState(contactItemId, true)
        }
        return multiselectIdList.isNotEmpty()
    }

    private fun updateCheckedState(contactItemId: Int, isChecked: Boolean) {
        val contactIndex = getContactIndexInList(contactItemId)
        _contactList.update {
            it.toMutableList().apply {
                set(
                    contactIndex, get(contactIndex).copy(
                        isChecked = isChecked
                    )
                )
            }
        }
    }

    private fun getContactIndexInList(contactItemId: Int): Int =
        _contactList.value.indexOfFirst { it.contact.id == contactItemId }


    override fun setLoadingStatus(contactItemId: Int, isLoading: Boolean) {
        val contactIndex = getContactIndexInList(contactItemId)
        _contactList.update {
            it.toMutableList().apply {
                set(
                    contactIndex, get(contactIndex).copy(
                        isLoading = isLoading
                    )
                )
            }
        }
    }

    override fun clearMultiselect() {
        multiselectIdList.forEach { id ->
            _contactList.update {
                it.map { contactItem ->
                    if (contactItem.contact.id == id) {
                        contactItem.copy(isChecked = false)
                    } else {
                        contactItem
                    }
                }
            }
        }
        multiselectIdList.clear()
    }

}