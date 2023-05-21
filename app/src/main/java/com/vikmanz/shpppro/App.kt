package com.vikmanz.shpppro

import android.app.Application
import android.content.ContentResolver
import dagger.hilt.android.HiltAndroidApp

/**
 * Singleton for send ContentResolver to ContactsPhoneInfoTaker()
 */
@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        app = this
        super.onCreate()
    }

    companion object {
        private lateinit var app: App
        val contentResolver: ContentResolver get() = app.contentResolver
    }
}