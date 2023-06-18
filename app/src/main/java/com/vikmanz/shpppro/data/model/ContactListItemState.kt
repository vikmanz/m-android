package com.vikmanz.shpppro.data.model

data class ContactListItemState(
    val contact: Contact,
    val onCheck: () -> Unit
)
