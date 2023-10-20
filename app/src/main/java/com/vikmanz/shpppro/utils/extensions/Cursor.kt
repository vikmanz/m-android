package com.vikmanz.shpppro.utils.extensions

import android.database.Cursor

fun Cursor.getColumnIndexFromResource(id: String): Int {
    this.use {
        return it.getColumnIndex(id)
    }
}