package com.vikmanz.shpppro.data.model

data class ContactItem(
    val contact: User,
    var isChecked: Boolean = false,
    val onCheck: () -> Unit = {}
)
