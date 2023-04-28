package com.vikmanz.shpppro.navigator

import com.vikmanz.shpppro.ui.base.BaseArgument

interface Navigator {

    fun launchStartFragment(argument: BaseArgument)

    fun launchMyContacts(argument: BaseArgument)

    fun goBack(result: Any? = null)

    fun toast(messageRes: Int)

    fun getString(messageRes: Int): String

}