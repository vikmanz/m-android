package com.vikmanz.shpppro.data.utils

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.provider.ContactsContract
import com.vikmanz.shpppro.App
import com.vikmanz.shpppro.constants.Constants.MAX_PHONE_IMPORT_CONTACTS_COUNT

@SuppressLint("Range")
class ContactsPhoneInfoTaker {

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
            ContactsContract.CommonDataKinds.Phone.PHOTO_URI,
            ContactsContract.CommonDataKinds.Phone.NUMBER
        )
        val selection =
            ContactsContract.Data.MIMETYPE + "='" + ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE + "'"
        val sortOrder = ContactsContract.Contacts.DISPLAY_NAME

        val cursor = contentResolver.query(uri, projection, selection, null, sortOrder)

        cursor?.let {
            while (cursor.moveToNext()) {

                val id = cursor.getLong(cursor.getColumnIndex(ContactsContract.Data.CONTACT_ID))

                val name: String = cursor.getString(
                    cursor.getColumnIndex(
                        ContactsContract.Contacts.DISPLAY_NAME
                    )
                )

                val photoUri: String = cursor.getString(
                    cursor.getColumnIndex(
                        ContactsContract.CommonDataKinds.Phone.PHOTO_URI
                    )
                ) ?: ""

                val phone: String = cursor.getString(
                    cursor.getColumnIndex(
                        ContactsContract.CommonDataKinds.Phone.NUMBER
                    )
                )

                val company: String = getInfoForType(
                    contentResolver,
                    id,
                    ContactsContract.CommonDataKinds.Organization.COMPANY,
                    ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE
                    )

                val email: String = getInfoForType(
                    contentResolver,
                    id,
                    ContactsContract.CommonDataKinds.Email.ADDRESS,
                    ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE
                )
                listOfContactsInformation.add(listOf(name, photoUri, phone, email, company))

                if (listOfContactsInformation.size == MAX_PHONE_IMPORT_CONTACTS_COUNT) break
            }
            cursor.close()
        }
        return listOfContactsInformation
    }

    /**
     * Take info from table fields via column id and content type.
     */
    private fun getInfoForType(
        resolver: ContentResolver,
        contactId: Long,
        column: String,
        contentType: String
    ): String {
        val uri = ContactsContract.Data.CONTENT_URI
        val projection = arrayOf(column)
        val selection =
            ContactsContract.Data.MIMETYPE + "='" +
                    contentType + "' AND " +
                    ContactsContract.CommonDataKinds.Organization.CONTACT_ID + " = ?"
        val selectionArgs = arrayOf(contactId.toString())
        val cursor = resolver.query(uri, projection, selection, selectionArgs, null)

        if (cursor != null && cursor.moveToFirst()) {
            val result = cursor.getString(cursor.getColumnIndex(column))
            cursor.close()
            return result
        }
        return ""
    }
}