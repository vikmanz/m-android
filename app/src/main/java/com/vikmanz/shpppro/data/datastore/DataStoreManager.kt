package com.vikmanz.shpppro.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.vikmanz.shpppro.data.datastore.interfaces.MyPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Data Store manager. Save and load user data from memory.
 */
class DataStoreManager @Inject constructor(
    @ApplicationContext applicationContext: Context
) : MyPreferences {

    /**
     * Application context.
     */
    private val appContext = applicationContext

    /**
     * Get name, password and autologin, and save all these in memory.
     */
    override suspend fun saveUserSata(name: String) {
        appContext.dataStore.edit {
            it[USER_LOGIN_KEY] = name
        }
    }

    /**
     * Clear user data in memory.
     */
    override suspend fun clearUser() {
        appContext.dataStore.edit {
            it[USER_LOGIN_KEY] = ""
        }
    }

    /**
     * Return user login as Flow.
     */
    override val userName: Flow<String> = appContext.dataStore.data.map {
        it[USER_LOGIN_KEY] ?: ""
    }

    /**
     * Companion object with keys of Data Store Preferences fields.
     */
    companion object {
        private const val DATA_STORE_NAME = "auth"
        private const val DS_USER_NAME = "user_name"

        private val USER_LOGIN_KEY = stringPreferencesKey(DS_USER_NAME)
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = DATA_STORE_NAME)
    }
}