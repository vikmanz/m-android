package com.vikmanz.shpppro.data.datastore.interfaces

import kotlinx.coroutines.flow.Flow

interface MyPreferences {

    suspend fun saveUserSata(name: String)
    suspend fun clearUser()

    val userName: Flow<String>

}