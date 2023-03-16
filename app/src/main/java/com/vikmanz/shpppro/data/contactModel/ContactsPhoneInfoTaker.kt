package com.vikmanz.shpppro.data.contactModel

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.provider.ContactsContract
import android.util.Log
import com.vikmanz.shpppro.utilits.log


class ContactsPhoneInfoTaker(private val contentResolver: ContentResolver) {

    @SuppressLint("Range", "Recycle")
    fun getPhonebookContactsInfo(): ArrayList<List<String>> {
        val uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
        val cursor = contentResolver.query(
            uri, null, null, null,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
        )
        val count = cursor?.count
        Log.d("myLog", "Total contacts count: $count")
        val listOfContactsInformation = ArrayList<List<String>>()

        if (count != null && count > 0) {
            while (cursor.moveToNext()) {
                val contactName =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                val contactNumber =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                log("Adding ::: Name: $contactName   Phone: $contactNumber")
                listOfContactsInformation.add(listOf(contactName, contactNumber))
            }
        }

        return listOfContactsInformation
    }

}
