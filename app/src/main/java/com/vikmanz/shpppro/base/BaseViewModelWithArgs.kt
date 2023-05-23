package com.vikmanz.shpppro.base

import androidx.navigation.NavArgs


abstract class BaseViewModelWithArgs<Args : NavArgs> : BaseViewModel() {

    protected lateinit var args: Args

    fun setNavArgs (arguments: Args) {
        args = arguments
    }
}