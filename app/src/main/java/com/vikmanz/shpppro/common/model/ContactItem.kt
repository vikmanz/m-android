package com.vikmanz.shpppro.common.model

data class ContactItem(
    val contact: Contact,
    var isChecked: Boolean = false,
    val onCheck: () -> Unit = {}
)
