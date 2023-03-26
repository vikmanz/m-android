package com.vikmanz.shpppro.data.contactModel

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.provider.ContactsContract
import android.util.Log
import com.vikmanz.shpppro.App
import com.vikmanz.shpppro.utilits.log

@SuppressLint("Range")
class ContactsPhoneInfoTaker() {

    /**
     * Take contacts from phonebook and return they as ArrayList<[name: String, phone: String]>.
     */

    fun getPhonebookContactsInfo(): ArrayList<List<String>> {

        val listOfContactsInformation = ArrayList<List<String>>()
        val contentResolver = App.instance.contentResolver

        val uri = ContactsContract.Data.CONTENT_URI
        val projection = arrayOf(
            ContactsContract.Data.CONTACT_ID,
            ContactsContract.Contacts.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER
        )
        val selection =
            ContactsContract.Data.MIMETYPE + "='" + ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE + "'"
        val sortOrder = ContactsContract.Contacts.DISPLAY_NAME

        val cursor = contentResolver.query(uri, projection, selection, null, sortOrder)

        cursor?.let {
            Log.d("myLog", "Total contacts count: ${cursor.count}")
            while (cursor.moveToNext()) {

                val id = cursor.getLong(cursor.getColumnIndex(ContactsContract.Data.CONTACT_ID))

                val name: String = cursor.getString(
                    cursor.getColumnIndex(
                        ContactsContract.Contacts.DISPLAY_NAME
                    )
                )

                val phone: String = cursor.getString(
                    cursor.getColumnIndex(
                        ContactsContract.CommonDataKinds.Phone.NUMBER
                    )
                )

                val company: String = getContactCompanyName(contentResolver, id)

                log("Adding ::: id: $id, name: $name, phone: $phone, company: $company")
                listOfContactsInformation.add(listOf(name, phone))

            }
            cursor.close()
        }
        return listOfContactsInformation
    }

    private fun getContactCompanyName(contentResolver: ContentResolver, id: Long): String {
        val uri = ContactsContract.Data.CONTENT_URI
        val projection = arrayOf(ContactsContract.CommonDataKinds.Organization.COMPANY)
        val selection =
            ContactsContract.Data.MIMETYPE + "='" +
                    ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE + "' AND " +
                    ContactsContract.CommonDataKinds.Organization.CONTACT_ID + " = ?"
        val selectionArgs = arrayOf(id.toString())
        val cursor = contentResolver.query(uri, projection, selection, selectionArgs, null)

        if (cursor != null && cursor.moveToFirst()) {
            val company =
                cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Organization.COMPANY))
            cursor.close()
            return company
        }
        return "[no have a company]"
    }

}
