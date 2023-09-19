package com.vikmanz.shpppro.data.datastore

import kotlinx.coroutines.flow.Flow

data class Credentials (
    val userEmail: String,
    val userPassword: String
)