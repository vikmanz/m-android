package com.vikmanz.shpppro.ui

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.net.Uri
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.vikmanz.shpppro.data.contactModel.ContactsService
import com.vikmanz.shpppro.data.contactModel.Contact
import com.vikmanz.shpppro.databinding.AddContactActivityMyContactsBinding
import com.vikmanz.shpppro.utilits.log
import com.vikmanz.shpppro.utilits.setContactPhoto
import com.vikmanz.shpppro.utilits.setContactPhotoFromUri

/**
 * Dialog Fragment in which user can add new contact with typed information to list of contacts.
 */
class AddContactDialogFragment(contactsService: ContactsService) : DialogFragment() {

    /**
     * Interface to send new contact from this dialog to MyContactsActivity (when contact will be added to contact list).
     */
    interface ConfirmationListener {
        fun addContactConfirmButtonClicked(contact: Contact)
    }

    /**
     * Contact service, which give fake images and create new contact from typed info.
     */
    private val _contactsService = contactsService

    /**
     * Uri for image, which will be take from galery.
     */
    private var imgUri: Uri? = null

    /**
     * Listener for send new contact from this activity to MyContactsActivity.
     */
    private lateinit var listener: ConfirmationListener

    /**
     * Binding of that Dialog Fragment.
     */
    private lateinit var _binding: AddContactActivityMyContactsBinding

    /**
     * I take it from internet and didn't change it.
     */
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

    /**
     *
     */
    @SuppressLint("UseGetLayoutInflater")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        // Restore state of uri.
        if (savedInstanceState != null) restoreImageUri(savedInstanceState)

        // inflate binding of dialog fragment
        _binding = AddContactActivityMyContactsBinding.inflate(LayoutInflater.from(context))

        return activity?.let {

            // create dialog
            val builder = AlertDialog.Builder(it)

            with(_binding) {
                // set avatar image
                updateAvatarImage()

                // set listener for change fake image and choose image from gallery
                imageviewAddcontactAvatar.setOnClickListener { requestDefaultImage() }
                buttonAddcontactAvatarFromGalery.setOnClickListener { requestImageFromGalery() }

                // set listener for create new contact and send it to MyContactsActivity
                buttonAddcontactSaveUser.setOnClickListener {
                    dialog?.dismiss()
                    listener.addContactConfirmButtonClicked(
                        _contactsService.getOneContact(
                            photoUrl = _contactsService.getCurrentContactPhotoUrl(),
                            photoUri = imgUri,
                            photoIndex = _contactsService.getCurrentPhotoCounter(),
                            name = textinputAddcontactUserNameInputfield.text.toString(),
                            career = textinputAddcontactUserCareerInputfield.text.toString(),
                            email = textinputAddcontactUserEmailInputfield.text.toString(),
                            phone = textinputAddcontactUserPhoneInputfield.text.toString(),
                            address = textinputAddcontactUserAddressInputfield.text.toString(),
                            birthday = textinputAddcontactUserBirthdayInputfield.text.toString()
                        )
                    )
                    _contactsService.incrementPhotoCounter()
                }

                // set listener for back button
                buttonAddcontactButtonBack.setOnClickListener { dialog?.cancel() }
            }

            // Set view and create dialog
            builder.setView(_binding.root).create()

        } ?: throw IllegalStateException("Activity cannot be null")
    }

    /**
     * Restore Uri of image when configuration changes.
     */
    private fun restoreImageUri(savedInstanceState: Bundle) {
        if (SDK_INT >= 33) {
            imgUri = savedInstanceState.getParcelable(PHOTO_STATE_KEY, Uri::class.java)
        } else {
            @Suppress("DEPRECATION")
            imgUri = savedInstanceState.getParcelable(PHOTO_STATE_KEY) as? Uri
        }
        log("uri loaded! uri - $imgUri")
    }

    /**
     * Get fake image.
     */
    private fun requestDefaultImage() {
        _contactsService.incrementPhotoCounter()
        if (imgUri != null) imgUri = null
        updateAvatarImage()
    }

    /**
     * Start activity with request image from gallery.
     */
    private fun requestImageFromGalery() {
        requestImageLauncher.launch(REQUEST_IMAGE_FROM_GALLERY)
    }

    /**
     * Register activity for result for request image from gallery.
     */
    private val requestImageLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                imgUri = uri
                updateAvatarImage()
            }
        }

    /**
     * Update image in UV.
     */
    private fun updateAvatarImage() {
        if (imgUri != null) {
            _binding.imageviewAddcontactAvatar.setContactPhotoFromUri(imgUri?:Uri.EMPTY)
            log("img update - $imgUri")
        } else {
            _binding.imageviewAddcontactAvatar.setContactPhoto(_contactsService.getCurrentContactPhotoUrl())
            log("img update -default")
        }
    }

    /**
     * Save state of avatar image uri.
     */
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(PHOTO_STATE_KEY, imgUri)
    }

    /**
     * Constants.
     */
    companion object {
        // input for request image launcher.
        private const val REQUEST_IMAGE_FROM_GALLERY = "image/*"

        // Save/Load State Keys. Don't need to change.
        private const val PHOTO_STATE_KEY = "PHOTO_ADD_CONTACT_DIALOG"
    }

}