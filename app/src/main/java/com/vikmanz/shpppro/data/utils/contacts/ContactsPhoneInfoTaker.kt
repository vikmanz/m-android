package com.vikmanz.shpppro.data.utils.contacts

import android.content.ContentResolver
import android.provider.ContactsContract
import com.vikmanz.shpppro.utils.extensions.getColumnIndexFromResource

private const val MAX_PHONE_IMPORT_CONTACTS_COUNT = 8

class ContactsPhoneInfoTaker {

    /**
     * Take contacts from phonebook and return they as ArrayList<[name: String, phone: String]>.
     */
    fun getPhonebookContactsInfo(contentResolver: ContentResolver): ArrayList<List<String>> {

        val listOfContactsInformation = ArrayList<List<String>>()

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

        cursor?.use { it ->
            while (it.moveToNext()) {

                val id = it.getLong(
                    it.getColumnIndexFromResource(
                        ContactsContract.Data.CONTACT_ID
                    )
                )

                val name: String = it.getString(
                    it.getColumnIndexFromResource(
                        ContactsContract.Contacts.DISPLAY_NAME
                    )
                )

                val photoUri: String = it.getString(
                    it.getColumnIndexFromResource(
                        ContactsContract.CommonDataKinds.Phone.PHOTO_URI
                    )
                ) ?: ""

                val phone: String = it.getString(
                    it.getColumnIndexFromResource(
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

        cursor?.use { it ->
            if (it.moveToFirst()) {
                val result = it.getString(it.use { it.getColumnIndex(column) })
                it.close()
                return result
            }
        }
        return ""
    }
}