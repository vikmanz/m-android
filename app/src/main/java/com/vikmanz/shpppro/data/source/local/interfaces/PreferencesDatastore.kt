package com.vikmanz.shpppro.data.source.local.interfaces

import kotlinx.coroutines.flow.Flow

interface PreferencesDatastore {

    suspend fun saveUserSata(name: String)
    suspend fun clearUser()

    val userName: Flow<String>

}