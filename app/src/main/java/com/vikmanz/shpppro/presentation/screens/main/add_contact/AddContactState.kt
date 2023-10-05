package com.vikmanz.shpppro.presentation.screens.main.add_contact

data class AddContactState(
    val isLoadingUsers: Boolean = false,
    val isSearchMode: Boolean = false,

    val contactEmail: String = "",
    val isShowAlertDialog: Boolean = false,
    val onAcceptAlertDialog: () -> Unit = {},
    val onDismissAlertDialog: () -> Unit = {}
)