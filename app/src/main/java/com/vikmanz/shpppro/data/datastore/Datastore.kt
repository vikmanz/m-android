package com.vikmanz.shpppro.data.datastore

import com.vikmanz.shpppro.data.model.Credentials
import kotlinx.coroutines.flow.Flow

interface Datastore {

    suspend fun saveUserData(name: String, password: String)
    suspend fun clearUserData()

    val userCredentials: Flow<Credentials?>

}