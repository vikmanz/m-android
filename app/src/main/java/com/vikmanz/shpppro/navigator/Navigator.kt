package com.vikmanz.shpppro.navigator

import com.example.fragmentsnavigatortest.screens.base.BaseArgs

interface Navigator {

    fun launchMyProfile(baseArgs: BaseArgs)

    fun launchMyContacts(baseArgs: BaseArgs)

    fun goBack(result: Any? = null)

    fun toast(messageRes: Int)

    fun getString(messageRes: Int): String

}