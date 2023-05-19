package com.vikmanz.shpppro.presentation.utils.extensions

import android.content.Context
import android.content.Intent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import com.vikmanz.shpppro.R
import com.vikmanz.shpppro.constants.Constants
import com.vikmanz.shpppro.presentation.main.MainActivity

fun Fragment.startMainActivity(email: String) {
    val activity = requireActivity()
    val intentObject = Intent(activity, MainActivity::class.java)
    intentObject.putExtra(Constants.INTENT_EMAIL_ID, email)
    startActivity(intentObject)
    activity.overridePendingTransition(R.anim.zoom_in_inner, R.anim.zoom_in_outter)
    activity.finish()
}

fun Fragment.hideKeyboard(view: View) {
    val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}