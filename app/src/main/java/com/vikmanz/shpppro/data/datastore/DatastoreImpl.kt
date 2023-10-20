package com.vikmanz.shpppro.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.vikmanz.shpppro.data.model.Credentials
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Data Store manager. Save and load user data from memory.
 */
class DatastoreImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : Datastore {


    /**
     * Get name, password and autologin, and save all these in memory.
     */
    override suspend fun saveUserData(
        name: String,
        password: String
    ) {
        context.dataStore.edit {
            it[EMAIL_INDEX] = name
            it[PASSWORD_INDEX] = password
        }
    }

    /**
     * Clear user data in memory.
     */
    override suspend fun clearUserData() {
        withContext(Dispatchers.IO) {
            context.dataStore.edit {
                it[EMAIL_INDEX] = ""
                it[PASSWORD_INDEX] = ""
            }
        }
    }

    /**
     * Return user login as Flow.
     */
    override val userCredentials: Flow<Credentials?> = context.dataStore.data.map {
        val email = it[EMAIL_INDEX] ?: ""
        val password = it[PASSWORD_INDEX] ?: ""
        if (email.isNotEmpty() && password.isNotEmpty()) Credentials(email, password) else null
    }

    /**
     * Companion object with keys of Data Store Preferences fields.
     */
    companion object {

        private const val DATA_STORE_NAME = "auth"
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
            name = DATA_STORE_NAME
        )

        private const val DATA_STORE_LOGIN_KEY = "user_name"
        private val EMAIL_INDEX = stringPreferencesKey(DATA_STORE_LOGIN_KEY)

        private const val DATA_STORE_PASSWORD_KEY = "user_password"
        private val PASSWORD_INDEX = stringPreferencesKey(DATA_STORE_PASSWORD_KEY)

    }
}