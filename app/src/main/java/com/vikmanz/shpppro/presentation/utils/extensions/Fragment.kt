package com.vikmanz.shpppro.presentation.utils.extensions

import android.view.WindowManager
import androidx.fragment.app.Fragment

fun Fragment.hideKeyboard() =
    requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)