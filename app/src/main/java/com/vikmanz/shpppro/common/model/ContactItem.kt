package com.vikmanz.shpppro.common.model

data class ContactItem(
    val contact: User,
    var isChecked: Boolean = false,
    val onCheck: () -> Unit = {}
)
