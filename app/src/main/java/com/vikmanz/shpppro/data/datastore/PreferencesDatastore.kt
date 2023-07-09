package com.vikmanz.shpppro.data.datastore

import kotlinx.coroutines.flow.Flow

interface PreferencesDatastore {

    suspend fun saveUserSata(name: String)
    suspend fun clearUser()

    val userName: Flow<String>

}