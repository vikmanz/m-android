package com.vikmanz.shpppro

import android.app.Application
import com.vikmanz.shpppro.data.ContactsRepository

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