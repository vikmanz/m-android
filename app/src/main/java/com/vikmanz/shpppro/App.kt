package com.vikmanz.shpppro


import android.app.Application
import com.vikmanz.shpppro.data.ContactsService

/**
 * Singleton for send ContentResolver to ContactsPhoneInfoTaker().
 */

class App : Application() {

    override fun onCreate() {
        app = this
        _contactsService = ContactsService()
        super.onCreate()
    }

    companion object {
        private lateinit var app: App
        val instance: App get() = app

        private lateinit var _contactsService: ContactsService
        val contactsService: ContactsService get() = _contactsService
    }

}