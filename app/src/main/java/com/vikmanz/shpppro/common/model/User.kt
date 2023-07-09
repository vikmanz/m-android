package com.vikmanz.shpppro.common.model

import java.io.Serializable

data class User(
    val id:Int? = null,
    val email: String? = null,
    var name: String? = null,
    var phone: String? = null,
    val address: String? = null,
    val career: String? = null,
    val birthday:String? = null,
    val facebook: String? = null,
    val instagram: String? = null,
    val twitter: String? = null,
    val linkedin: String? = null,
    val image: String? = null
): Serializable