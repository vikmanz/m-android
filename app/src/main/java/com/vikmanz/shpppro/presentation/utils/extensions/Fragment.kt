package com.vikmanz.shpppro.presentation.utils.extensions

import android.content.Intent
import android.view.WindowManager
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

fun Fragment.hideKeyboard() =
    requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)