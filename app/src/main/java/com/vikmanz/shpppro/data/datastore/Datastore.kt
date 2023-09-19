package com.vikmanz.shpppro.data.datastore

import kotlinx.coroutines.flow.Flow

interface Datastore {

    suspend fun saveUserSata(name: String, password: String)
    suspend fun clearUser()

    val userEmail: Flow<String>
    val userPassword: Flow<String>

}