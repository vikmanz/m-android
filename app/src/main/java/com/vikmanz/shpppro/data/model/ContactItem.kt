package com.vikmanz.shpppro.data.model

import com.vikmanz.shpppro.data.model.interfaces.ContactItemInterface

data class ContactItem(
    override val contact: User,
    val onDelete: (ContactItem) -> Unit = {},
    val isLoading: Boolean = false,
    val isChecked: Boolean = false,
    val onClick: (ContactItem) -> Unit = {},
    val onPlusClick: (ContactItem) -> Unit = {},
    val onLongClick: (ContactItem) -> Unit = {},
) : ContactItemInterface
