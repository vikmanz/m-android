package com.vikmanz.shpppro.presentation.utils.extensions

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment

fun Fragment.setKeyboardVisibility(isShowKeyboard: Boolean, view: View? = null) {
    val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    val viewToHide = view ?: requireView()

    if (isShowKeyboard) {
        viewToHide.requestFocus()
        imm.showSoftInput(viewToHide, InputMethodManager.SHOW_IMPLICIT)
    } else {
        imm.hideSoftInputFromWindow(viewToHide.windowToken, 0)
    }
}

fun Fragment.startDeclineAccessActivity() {
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
    intent.data = Uri.fromParts("package", requireContext().packageName, null)
    startServiceActivity(intent)
}

fun Fragment.startChangeLanguageActivity() {
    val intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
    startServiceActivity(intent)
}

fun Fragment.startServiceActivity(intent: Intent) =
    with(intent) {
        addCategory(Intent.CATEGORY_DEFAULT)
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
        addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
        startActivity(this)
    }

