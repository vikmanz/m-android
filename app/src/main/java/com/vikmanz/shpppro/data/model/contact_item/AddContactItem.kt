package com.vikmanz.shpppro.data.model.contact_item

import com.vikmanz.shpppro.data.model.contact_item.interfaces.ContactItemInterface
import com.vikmanz.shpppro.data.model.User

data class AddContactItem(
    override val contact: User,
    val isLoading: Boolean = false,
    val isAdded: Boolean = false,
    val isError: Boolean = false,
    val onClick: (AddContactItem) -> Unit = {},
    val onPlusClick: (AddContactItem) -> Unit = {},
) : ContactItemInterface
