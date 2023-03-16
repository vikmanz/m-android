package com.vikmanz.shpppro.ui

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.vikmanz.shpppro.R

/**
 * Dialog, which created when user decline permission of read phone contacts list.
 */
class OnDeclinePermissionDialogFragment : DialogFragment() {

    /**
     * Dialog with attention.
     */
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        MaterialAlertDialogBuilder(requireContext(), R.style.MaterialAlertDialog_rounded)
            .setTitle("Permission denied!")
            .setMessage("Please, go to settings and set contacts permission to allow.")
            .setPositiveButton("Ok") { _,_ -> goToGrantPermission() }
            .setNegativeButton("Cancel", null)
            .create()

    /**
     * If user click "Ok" in dialog, we open settings of this app, where user can set needed permission to allow.
     */
    private fun goToGrantPermission() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        with(intent) {
            data = Uri.fromParts("package", activity?.applicationContext?.packageName, null)
            addCategory(Intent.CATEGORY_DEFAULT)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
            addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
        }
        startActivity(intent)
    }
}