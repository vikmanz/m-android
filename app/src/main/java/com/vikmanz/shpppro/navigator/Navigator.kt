package com.vikmanz.shpppro.navigator

import com.vikmanz.shpppro.ui.base.BaseArgs

interface Navigator {

    fun launchMyProfile(baseArgs: BaseArgs)

    fun launchMyContacts(baseArgs: BaseArgs)

    fun goBack(result: Any? = null)

    fun toast(messageRes: Int)

    fun getString(messageRes: Int): String

}