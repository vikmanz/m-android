package com.vikmanz.shpppro.ui.main.main_fragment

import androidx.lifecycle.SavedStateHandle
import com.vikmanz.shpppro.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewPagerFragmentViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private val navArgs = MainViewPagerFragmentArgs.fromSavedStateHandle(savedStateHandle)

    val userEmail = navArgs.email

}