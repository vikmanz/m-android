package com.vikmanz.shpppro.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Data Store manager. Save and load user data from memory.
 */
class DataStoreManager (private val context: Context) {

    /**
     * Companion object with keys of Data Store Preferences fields.
     */
    companion object {
        // Key for preferences Data Store.
        private const val DATA_STORE_NAME = "auth"
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = DATA_STORE_NAME)

        // Data Store Keys. Don't need to change.
        private const val DS_USER_NAME = "user_name"
        private const val DS_USER_PASSWORD = "user_password"
        private const val DS_USER_AUTOLOGIN_STATUS = "user_login_status"

        private val USER_LOGIN_KEY = stringPreferencesKey(DS_USER_NAME)
        private val USER_PASSWORD_KEY = stringPreferencesKey(DS_USER_PASSWORD)
        private val LOGIN_STATUS_KEY = booleanPreferencesKey(DS_USER_AUTOLOGIN_STATUS)
    }

    /**
     * Get name, password and autologin, and save all these in memory.
     */
    suspend fun saveUserSata(name: String, password: String, isAutoLogin: Boolean) {
        context.dataStore.edit {
            it[USER_LOGIN_KEY] = name
            it[USER_PASSWORD_KEY] = password
            it[LOGIN_STATUS_KEY] = isAutoLogin
        }
    }

    /**
     * Clear user data in memory.
     */
    suspend fun clearUser() {
        context.dataStore.edit {
            it[USER_LOGIN_KEY] = ""
            it[USER_PASSWORD_KEY] = ""
            it[LOGIN_STATUS_KEY] = false
        }
    }

    /**
     * Return user login as Flow.
     */
    val userNameFlow: Flow<String> = context.dataStore.data.map {
        it[USER_LOGIN_KEY] ?: ""
    }

    /**
     * Return user password as Flow.
     */
    @Suppress("unused")
    val userPasswordFlow: Flow<String> = context.dataStore.data.map {
        it[USER_PASSWORD_KEY] ?: ""
    }

    /**
     * Return user autologin status as Flow.
     */
    val userLoginStatusFlow: Flow<Boolean> = context.dataStore.data.map {
        it[LOGIN_STATUS_KEY] ?: false
    }

}