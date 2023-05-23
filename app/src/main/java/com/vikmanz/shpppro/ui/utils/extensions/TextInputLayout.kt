package com.vikmanz.shpppro.ui.utils.extensions

import com.google.android.material.textfield.TextInputLayout

fun TextInputLayout.clearError() {
    this.helperText = null
}