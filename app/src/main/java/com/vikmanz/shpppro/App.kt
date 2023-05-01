package com.vikmanz.shpppro


import android.app.Application
import com.vikmanz.shpppro.data.ContactsRepository

/**
 * Singleton for send ContentResolver to ContactsPhoneInfoTaker().
 */

class App : Application() {

    override fun onCreate() {
        app = this
        _contactsRepository = ContactsRepository()
        super.onCreate()
    }

    companion object {
        private lateinit var app: App
        val instance: App get() = app

        private lateinit var _contactsRepository: ContactsRepository
        val contactsRepository: ContactsRepository get() = _contactsRepository
    }

}