package com.vikmanz.shpppro.presentation.screens.auth.sign_up_extended

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.vikmanz.shpppro.common.Constants.LOGIN_VIEW_FIRST
import com.vikmanz.shpppro.common.extensions.log
import com.vikmanz.shpppro.common.extensions.swapBoolean
import com.vikmanz.shpppro.data.datastore.Datastore
import com.vikmanz.shpppro.domain.usecases.account.EditUserUseCase
import com.vikmanz.shpppro.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ua.digitalminds.fortrainerapp.data.result.ApiResult
import javax.inject.Inject

private const val DEFAULT_SHOW_HELPERS = false

@HiltViewModel
class SignUpExtendedViewModel @Inject constructor(
    private val editUserUseCase: EditUserUseCase
) : BaseViewModel() {

    private val doNavigation = {
        val direction =
            SignUpExtendedFragmentDirections.startMainActivity()
        navigateToActivity(direction)
    }


    fun onCancelClick() {
        doNavigation()
    }

    fun onSaveClick(username: String, phone: String) {

        viewModelScope.launch(Dispatchers.IO) {
            log("Start coroutine")
            editUserUseCase(
                name = username,
                phone = phone
            ).collect {

                when (it) {

                    is ApiResult.Loading -> {
                        log("loading")
                    }

                    is ApiResult.Success -> {
                        log("api success")
                        log(it.value.toString())
                        doNavigation()
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