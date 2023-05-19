package com.vikmanz.shpppro


import android.app.Application
import com.vikmanz.shpppro.data.ContactsReposetory

/**
 * Singleton for send ContentResolver to ContactsPhoneInfoTaker().
 */

class App : Application() {

    override fun onCreate() {
        app = this
        _contactsReposetory = ContactsReposetory()
        super.onCreate()
    }

    companion object {
        private lateinit var app: App
        val instance: App get() = app

        private lateinit var _contactsReposetory: ContactsReposetory
        val contactsReposetory: ContactsReposetory get() = _contactsReposetory
    }

}