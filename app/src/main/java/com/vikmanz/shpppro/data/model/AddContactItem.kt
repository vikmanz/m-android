package com.vikmanz.shpppro.data.model

import com.vikmanz.shpppro.data.model.interfaces.ContactItemInterface

data class AddContactItem(
    override val contact: User,
    val isLoading: Boolean = false,
    val isAdded: Boolean = false,
    val isError: Boolean = false,
    val onClick: (AddContactItem) -> Unit = {},
    val onPlusClick: (AddContactItem) -> Unit = {},
) : ContactItemInterface
