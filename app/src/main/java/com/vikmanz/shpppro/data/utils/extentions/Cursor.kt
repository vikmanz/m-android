package com.vikmanz.shpppro.data.utils.extentions

import android.database.Cursor

fun Cursor.getColumnIndexFromResource(id: String): Int {
    this.use {
        return it.getColumnIndex(id)
    }
}