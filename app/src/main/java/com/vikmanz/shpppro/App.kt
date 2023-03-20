package com.vikmanz.shpppro


import android.app.Application

/**
 * Singleton for send ContentResolver to ContactsPhoneInfoTaker().
 */

class App : Application() {

    override fun onCreate() {
        app = this
        super.onCreate()
    }

    companion object {
        private lateinit var app: App
        val instance: App get() = app
    }

}