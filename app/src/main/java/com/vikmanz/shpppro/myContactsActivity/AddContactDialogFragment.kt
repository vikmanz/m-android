package com.vikmanz.shpppro.myContactsActivity

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.vikmanz.shpppro.databinding.AddContactActivityMyContactsBinding
import com.vikmanz.shpppro.myContactsActivity.contactModel.Contact
import com.vikmanz.shpppro.myContactsActivity.contactModel.ContactsService


class AddContactDialogFragment(contactsService: ContactsService) : DialogFragment() {

    interface ConfirmationListener {
        fun confirmButtonClicked(contact: Contact, imgCounter: Int)
        //fun cancelButtonClicked()
    }

    private val _contactsService = contactsService


    private lateinit var listener: ConfirmationListener

    private lateinit var _binding: AddContactActivityMyContactsBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)

        try {
            // Instantiate the ConfirmationListener so we can send events to the host
            listener = activity as ConfirmationListener
        } catch (e: ClassCastException) {
            // The activity doesn't implement the interface, throw exception
            throw ClassCastException(activity.toString() + " must implement ConfirmationListener")
        }
    }

    @SuppressLint("UseGetLayoutInflater")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = AddContactActivityMyContactsBinding.inflate(LayoutInflater.from(context))
        return activity?.let {
            val builder = AlertDialog.Builder(it)

            with(_binding) {
                imageView.setContactPhoto(_contactsService.getCurrentContactPhotoUrl())
                ibNextPhoto.setOnClickListener {
                   imageView.setContactPhoto(_contactsService.getNextContactPhotoUrl())
                }
                ibPreviousPhoto.setOnClickListener {
                    imageView.setContactPhoto(_contactsService.getPreviousContactPhotoUrl())
                }
            }

            // Pass null as the parent view because its going in the dialog layout
            builder
                .setView(_binding.root)

                .setTitle("Write new contact information")

                // Add action buttons
                .setPositiveButton("Add contact",
                    DialogInterface.OnClickListener { _, _ ->
                        _contactsService.incrementPhotoCounter()
                        listener.confirmButtonClicked(
                            Contact(
                                contactId = Math.random().toLong(),
                                contactPhotoUrl = _contactsService.getCurrentContactPhotoUrl(),
                                contactName = _binding.ti1.getEditText()?.getText().toString(),
                                contactCareer = _binding.ti2.getEditText()?.getText().toString(),
                        ),
                            _contactsService.getCurrentPhotoCounter()
                        )
                    })
                .setNegativeButton("Cancel",
                    DialogInterface.OnClickListener { _, _ -> })

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

}