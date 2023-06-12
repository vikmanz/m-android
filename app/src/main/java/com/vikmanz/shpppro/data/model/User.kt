package com.vikmanz.shpppro.data.model

data class User(
    val id:Int,
    val email: String,
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
)