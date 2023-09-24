package com.vikmanz.shpppro.data.model

data class ContactItem(
    val contact: User,
    val onDelete: (ContactItem) -> Unit = {},
    val isLoading: Boolean = false,
    val isChecked: Boolean = false,
    val onClick: (ContactItem) -> Unit = {},
    val onLongClick: (ContactItem) -> Unit = {},
)
