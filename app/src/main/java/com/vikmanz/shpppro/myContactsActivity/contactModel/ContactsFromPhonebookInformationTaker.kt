package com.vikmanz.shpppro.myContactsActivity.contactModel

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.pm.PackageManager
import android.provider.ContactsContract
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class ContactsFromPhonebookInformationTaker(private val activity: AppCompatActivity, private val contentResolver: ContentResolver) {

    fun getContactsInfo(): ArrayList<List<String>> {
        requestPermission()
        return getPhonebookContactsInformation()
    }

    private fun requestPermission() {
        if (ContextCompat.checkSelfPermission(
                activity,
                Manifest.permission.READ_CONTACTS
            ) != PackageManager.PERMISSION_GRANTED.toInt()
        ) {
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(Manifest.permission.READ_CONTACTS),
                0
            )
        }
    }

    @SuppressLint("Range")
    private fun getPhonebookContactsInformation(): ArrayList<List<String>> {
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
                Log.d("myLog", "Adding ::: Name: $contactName   Phone: $contactNumber")
                listOfContactsInformation.add(listOf(contactName, contactNumber))
            }
        }
        return listOfContactsInformation
    }


}
