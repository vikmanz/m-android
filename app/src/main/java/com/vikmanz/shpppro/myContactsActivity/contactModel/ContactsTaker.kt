package com.vikmanz.shpppro.myContactsActivity.contactModel

import android.content.ContentResolver
import android.provider.ContactsContract

class ContactsTaker {

    init {
       // val contentResolver: ContentResolver = ContentResolver(context)

    }

    fun getContacts(contentResolver: ContentResolver): List<Pair<String, String>> {
        val contacts = mutableListOf<Pair<String, String>>()

        val projection = arrayOf(
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER
        )

        val cursor = contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            projection,
            null,
            null,
            null
        )

        cursor?.use {
            val nameIndex = it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
            val numberIndex = it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)

            while (it.moveToNext()) {
                val name = it.getString(nameIndex)
                val number = it.getString(numberIndex)
                contacts.add(Pair(name, number))
            }
        }

        return contacts
    }

}
