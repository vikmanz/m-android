package com.vikmanz.shpppro.presentation.screens.main.main_fragment.my_contacts_list

data class MyContactsListState(

    val isLoadingData: Boolean = false,
    val isMultiselectMode: Boolean = false,
    val isShowSnackBar: Boolean = false,
    val isSearchMode: Boolean = false,
    val isContactsEmpty: Boolean = false

)
