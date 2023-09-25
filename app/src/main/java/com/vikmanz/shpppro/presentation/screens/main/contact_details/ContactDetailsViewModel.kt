package com.vikmanz.shpppro.presentation.screens.main.contact_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.vikmanz.shpppro.common.extensions.log
import com.vikmanz.shpppro.data.model.ContactItem
import com.vikmanz.shpppro.data.model.User
import com.vikmanz.shpppro.domain.repository.ShPPContactsRepository
import com.vikmanz.shpppro.domain.usecases.contacts.GetContactUseCase
import com.vikmanz.shpppro.presentation.base.BaseViewModel
import com.vikmanz.shpppro.presentation.utils.extensions.alsoLog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ua.digitalminds.fortrainerapp.data.result.ApiResult
import javax.inject.Inject

@HiltViewModel
class ContactDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
   // val getContactUseCase: GetContactUseCase
) : BaseViewModel() {

    private val navArgs = ContactDetailsFragmentArgs.fromSavedStateHandle(savedStateHandle)

    private val _user = MutableStateFlow(navArgs.user)
    val user = _user.asStateFlow()

    init {
        log(_user.value.toString())
    }

//    init {
//        getUser()
//    }
//
//    private fun getUser() {
//        val user = navArgs.user
//        viewModelScope.launch(Dispatchers.IO) {
//            log("Start coroutine getUser")
//            getContactUseCase(userId).collect {
//                when (it) {
//                    is ApiResult.NetworkError -> User() alsoLog "network error!"
//                    is ApiResult.ServerError -> User() alsoLog "server error!"
//                    is ApiResult.Loading -> User() alsoLog "loading"
//                    is ApiResult.Success -> {
//                        _user.update { _ -> it.value }
//                }
//            }
//        }
//    } alsoLog "End coroutine getUser"
//    }

    fun onButtonBackPressed() {
        navigateBack()
    }

}