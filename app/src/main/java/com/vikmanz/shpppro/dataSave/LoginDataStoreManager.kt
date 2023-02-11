package com.vikmanz.shpppro.dataSave

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "auth")

class LoginDataStoreManager (private val context: Context) {

    companion object {
        private val USER_LOGIN_KEY = stringPreferencesKey("user_name")
        private val USER_PASSWORD_KEY = stringPreferencesKey("user_pass")
        private val LOGIN_STATUS_KEY = booleanPreferencesKey("user_login_status")
    }

    suspend fun saveUserSata(name: String, password: String, isAutoLogin: Boolean) {
        context.dataStore.edit {
            it[USER_LOGIN_KEY] = name
            it[USER_PASSWORD_KEY] = password
            it[LOGIN_STATUS_KEY] = isAutoLogin
        }
    }

    suspend fun clearUser() {
        context.dataStore.edit {
            it[USER_LOGIN_KEY] = ""
            it[USER_PASSWORD_KEY] = ""
            it[LOGIN_STATUS_KEY] = false
        }
    }

    val userNameFlow: Flow<String> = context.dataStore.data.map {
        it[USER_LOGIN_KEY] ?: ""
    }

    val userPasswordFlow: Flow<String> = context.dataStore.data.map {
        it[USER_PASSWORD_KEY] ?: ""
    }

    val userLoginStatusFlow: Flow<Boolean> = context.dataStore.data.map {
        it[LOGIN_STATUS_KEY] ?: false
    }

}