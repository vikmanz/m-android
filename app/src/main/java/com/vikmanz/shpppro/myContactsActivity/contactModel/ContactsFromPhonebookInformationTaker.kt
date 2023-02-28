package com.vikmanz.shpppro.myContactsActivity.contactModel

import android.Manifest.permission.READ_CONTACTS
import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.pm.PackageManager
import android.provider.ContactsContract
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.checkSelfPermission


class ContactsFromPhonebookInformationTaker(
    private val activity: AppCompatActivity,
    private val contentResolver: ContentResolver
) {

    var counter = 0;

    fun getContactsInfo(): ArrayList<List<String>>? {
        if (!requestPermission()) return null
        return getPhonebookContactsInformation()
    }

    private fun requestPermission(): Boolean {

        val permissionBeforeStatus = checkSelfPermission(activity, READ_CONTACTS);
        if (permissionBeforeStatus == PackageManager.PERMISSION_GRANTED) {
            val toast = Toast.makeText(
                activity,
                "Access already granted! ${counter++}",
                Toast.LENGTH_SHORT
            )
            toast.setMargin(50f, 50f)
            toast.show()
            return true
        } else {
            Log.d("myLog", "Request access! ${counter++}")
            val toast = Toast.makeText(
                activity,
                "Request access!",
                Toast.LENGTH_SHORT
            )
            toast.setMargin(50f, 50f)
            toast.show()
            activity.requestPermissions(arrayOf(READ_CONTACTS), 0)
        }

        val permissionAfterStatus = checkSelfPermission(activity, READ_CONTACTS);

        if (permissionAfterStatus == PackageManager.PERMISSION_GRANTED) {
            val toast = Toast.makeText(
                activity,
                "Access granted! ${counter++}",
                Toast.LENGTH_SHORT
            )
            toast.setMargin(50f, 50f)
            toast.show()
            return true
        } else {
            val toast = Toast.makeText(
                activity,
                "Access denied! ${counter++}",
                Toast.LENGTH_SHORT
            )
            toast.setMargin(50f, 50f)
            toast.show()
            return false

        }
    }

    @SuppressLint("Range", "Recycle")
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
