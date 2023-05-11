package com.vikmanz.shpppro


import android.annotation.SuppressLint
import android.app.Application
import com.vikmanz.shpppro.data.ContactsRepository
import com.vikmanz.shpppro.data.DataStoreManager

/**
 * Singleton for send ContentResolver to ContactsPhoneInfoTaker().
 */

class App : Application() {

    override fun onCreate() {
        app = this
        contactsRepositoryPrivate = ContactsRepository()
        super.onCreate()
    }

    companion object {
        private lateinit var app: App
        val instance: App get() = app

        private lateinit var contactsRepositoryPrivate: ContactsRepository
        val contactsRepository: ContactsRepository
            get() = contactsRepositoryPrivate
    }
}