package com.vikmanz.shpppro.data.user_token

import com.vikmanz.shpppro.data.model.User

@Suppress("MemberVisibilityCanBePrivate")
object UserTokenHandler {

    var user: User = User()
    var accessToken: String = ""
    var refreshToken: String = ""

    fun setUserData(
        user: User? = null,
        accessToken: String? = null,
        refreshToken: String? = null
    ) {
        user?.let { this.user = it }
        accessToken?.let { this.accessToken = "Bearer $it" }
        refreshToken?.let { this.refreshToken = "Bearer $it" }
    }

}