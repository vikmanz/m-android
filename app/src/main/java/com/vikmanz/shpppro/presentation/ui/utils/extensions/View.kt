package com.vikmanz.shpppro.presentation.ui.utils.extensions

import android.view.View

fun View.setVisible() {
    this.visibility = View.VISIBLE
}

fun setMultipleVisible(vararg input: View) {
    for (view in input) {
        view.setVisible()
    }
}

fun View.setInvisible() {
    this.visibility = View.INVISIBLE
}

fun setMultipleInvisible(vararg input: View) {
    for (view in input) {
        view.setInvisible()
    }
}
fun View.setGone() {
    this.visibility = View.GONE
}

fun setMultipleGone(vararg input: View) {
    for (view in input) {
        view.setGone()
    }
}




