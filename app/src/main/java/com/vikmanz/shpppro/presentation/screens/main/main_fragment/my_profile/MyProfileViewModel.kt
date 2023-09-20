package com.vikmanz.shpppro.presentation.screens.main.main_fragment.my_profile

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.vikmanz.shpppro.common.extensions.log
import com.vikmanz.shpppro.data.datastore.Datastore
import com.vikmanz.shpppro.data.model.User
import com.vikmanz.shpppro.domain.usecases.account.GetUserUseCase
import com.vikmanz.shpppro.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ua.digitalminds.fortrainerapp.data.result.ApiResult
import javax.inject.Inject

@HiltViewModel
class MyProfileViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    dataStore: Datastore,
    private val getUserUseCase: GetUserUseCase
) : BaseViewModel() {

    private val navArgs = MyProfileFragmentArgs.fromSavedStateHandle(savedStateHandle)
    private val _dataStore = dataStore

    private val _user = MutableStateFlow(User())
    val user = _user.asStateFlow()

    var userEmail = navArgs.email //"hard.codedviewmodel@gm.com" //

    init {
        getUserData()
    }

    fun onLogout() {
        clearSavedUserData()
    }

    private fun clearSavedUserData() {
        viewModelScope.launch {
            _dataStore.clearUser()
        }
    }

    private fun getUserData() {
        viewModelScope.launch(Dispatchers.IO) {
            log("Start coroutine")
            getUserUseCase().collect {
                when (it) {

                    is ApiResult.Loading -> {
                        log("loading")
                    }

                    is ApiResult.Success -> {
                        log("api success")
                        log(it.value.toString())
                        _user.update { _ -> it.value }
                    }

                    is ApiResult.NetworkError -> {
                        log("api network error!")
                    }

                    is ApiResult.ServerError -> {
                        log("api server error!")
                    }
                }
            }
            log("End coroutine")
        }
    }


}