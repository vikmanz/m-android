package com.vikmanz.shpppro.navigator

interface Navigator {

    fun launchMyProfile()

    fun launchMeContacts()

    fun goBack(result: Any? = null)

    fun toast(messageRes: Int)

    fun getString(messageRes: Int): String

}