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
        val projection =
            arrayOf(
            ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER,
            ContactsContract.CommonDataKinds.Organization.COMPANY
        )

        val selection = null
                                                                                        //            ContactsContract.Data.MIMETYPE + " in (?, ?) AND " +
                                                                                        //                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?"

        val selectionArgs =
            arrayOf(
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER,
            ContactsContract.CommonDataKinds.Organization.COMPANY
        )
        val sortOrder = ContactsContract.CommonDataKinds.Phone.CONTACT_ID

//        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
//        ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE,
//        ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE

        log("""
              _FROM [table_name]: $uri
              _projection [col,col,col,...]: ${arrayToString(projection)}
              _WHERE [WHERE col = value]: $selection
              _selectionArgs : ${arrayToString(selectionArgs)}
              _ORDER BY [ORDER BY col,col,...]: $sortOrder
           _""".trimMargin("_"))

        val cursor = contentResolver.query(uri, projection, selection, null, sortOrder)
        log("<<<--- cursor created --->>>")
        if (cursor != null) {

            log("count of columns ${cursor.columnCount}")
            log("columns: ${arrayToString(cursor.columnNames)}")
            log("count of lines ${cursor.count}")

            //cursor.close()
            log("--->>> cursor closed <<<---")

            while (cursor.moveToNext()) {

                val column0 =
                    cursor.getString(0)
                val column1 =
                    cursor.getString(1)
                val column2 =
                    cursor.getString(3)
                val column3 =
                    cursor.getString(3)

                log("\n[0]:$column0,   [1]:$column1, [2]:$column2, [3]:$column3")

            }
            cursor.close()
        }
//        val uri = ContactsContract.Data.CONTENT_URI
//        val projection = arrayOf(
//            ContactsContract.Data.CONTACT_ID,
//            ContactsContract.Contacts.DISPLAY_NAME,
//            ContactsContract.CommonDataKinds.Phone.NUMBER
//        )
//        val selection =
//            ContactsContract.Data.MIMETYPE + "='" + ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE + "'"
//        val sortOrder = ContactsContract.Contacts.DISPLAY_NAME
//
//        val cursor = contentResolver.query(uri, projection, selection, null, sortOrder)
//
//        cursor?.let {
//            Log.d("myLog", "Total contacts count: ${cursor.count}")
//            while (cursor.moveToNext()) {
//
//                val id = cursor.getLong(cursor.getColumnIndex(ContactsContract.Data.CONTACT_ID))
//
//                val name: String = cursor.getString(
//                    cursor.getColumnIndex(
//                        ContactsContract.Contacts.DISPLAY_NAME
//                    )
//                )
//
//                val phone: String = cursor.getString(
//                    cursor.getColumnIndex(
//                        ContactsContract.CommonDataKinds.Phone.NUMBER
//                    )
//                )
//
//                val company: String = getContactCompanyName(contentResolver, id)
//
//                log("Adding ::: id: $id, name: $name, phone: $phone, company: $company")
//                listOfContactsInformation.add(listOf(name, phone))
//
//            }
//            cursor.close()
//        }
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

    private fun arrayToString(array: Array<String>): String {
        var result = "array of ["
        array.forEach { result += "$it, " }
        result = result.substring(0, result.length - 2) + "]"
        return result
    }
}
