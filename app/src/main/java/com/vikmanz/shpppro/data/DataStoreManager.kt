package com.vikmanz.shpppro.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private const val DATA_STORE_NAME = "auth"
private const val DS_USER_NAME = "user_name"

/**
 * Data Store manager. Save and load user data from memory.
 */
class DataStoreManager (private val context: Context) {

    /**
     * Get name, password and autologin, and save all these in memory.
     */
    suspend fun saveUserSata(name: String) {
        context.dataStore.edit {
            it[USER_LOGIN_KEY] = name
        }
    }

    /**
     * Clear user data in memory.
     */
    suspend fun clearUser() {
        context.dataStore.edit {
            it[USER_LOGIN_KEY] = ""
        }
    }

    /**
     * Return user login as Flow.
     */
    val userNameFlow: Flow<String> = context.dataStore.data.map {
        it[USER_LOGIN_KEY] ?: ""
    }

    /**
     * Companion object with keys of Data Store Preferences fields.
     */
    companion object {
        private val USER_LOGIN_KEY = stringPreferencesKey(DS_USER_NAME)
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = DATA_STORE_NAME)
    }

}