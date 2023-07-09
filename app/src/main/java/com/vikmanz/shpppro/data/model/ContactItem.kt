package com.vikmanz.shpppro.data.model

data class ContactItem(
    val contact: Contact,
    var isChecked: Boolean = false,
    val onCheck: () -> Unit = {}
)
