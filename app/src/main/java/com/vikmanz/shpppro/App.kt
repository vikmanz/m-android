package com.vikmanz.shpppro

import android.annotation.SuppressLint
import android.app.Application
import android.content.ContentResolver
import com.vikmanz.shpppro.data.ContactsRepository
import com.vikmanz.shpppro.data.DataStoreManager

/**
 * Singleton for send ContentResolver to ContactsPhoneInfoTaker()
 */

class App : Application() {

    override fun onCreate() {
        app = this
        contactsRepositorySingleton = ContactsRepository()
        dataStoreSingleton = DataStoreManager(applicationContext)
        super.onCreate()
    }

    companion object {

        private lateinit var app: App
        val contentResolver: ContentResolver get() = app.contentResolver

        private lateinit var contactsRepositorySingleton: ContactsRepository
        val contactsRepository: ContactsRepository
            get() = contactsRepositorySingleton

        //It's normal! https://stackoverflow.com/questions/37709918/warning-do-not-place-android-context-classes-in-static-fields-this-is-a-memory
        @SuppressLint("StaticFieldLeak")
        private lateinit var dataStoreSingleton: DataStoreManager
        val dataStore: DataStoreManager
            get() = dataStoreSingleton
    }
}