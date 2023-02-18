package com.vikmanz.shpppro

import android.app.Application
import com.vikmanz.shpppro.myContactsActivity.contactModel.OneContactService

class App : Application() {

    // Singleton for contacts.
    val oneContactService = OneContactService()
}