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
        fun confirmButtonClicked(contact: Contact)
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
                imageViewAvatarAddContact.setContactPhoto(_contactsService.getCurrentContactPhotoUrl())
                buttonAddFromGaleryAddContact.setOnClickListener {
                    imageViewAvatarAddContact.setContactPhoto(_contactsService.getNextContactPhotoUrl())
                }
                buttonSaveAddNewContactActivityMyContacts.setOnClickListener {
                    dialog?.dismiss()
                    listener.confirmButtonClicked(
                        _contactsService.getOneContact(
                            id = Math.random().toLong(),
                            photoUrl = _contactsService.getCurrentContactPhotoUrl(),
                            photoIndex = _contactsService.getCurrentPhotoCounter(),
                            name = textInputUserNameAddContact.getEditText()?.getText().toString(),
                            career = textInputCareerAddContact.getEditText()?.getText().toString(),
                            email = textInputCareerAddContact.getEditText()?.getText().toString(),
                            phone = textInputCareerAddContact.getEditText()?.getText().toString(),
                            address = textInputCareerAddContact.getEditText()?.getText().toString(),
                            birthday = textInputCareerAddContact.getEditText()?.getText().toString()
                        )
                    )
                    _contactsService.incrementPhotoCounter()
                }
                buttonBackAddContact.setOnClickListener { dialog?.cancel() }
            }

            // Pass null as the parent view because its going in the dialog layout
            builder.setView(_binding.root).create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

}