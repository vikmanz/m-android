package com.vikmanz.shpppro.ui

import android.annotation.SuppressLint
import android.app.Dialog
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.vikmanz.shpppro.App
import com.vikmanz.shpppro.databinding.AddContactActivityMyContactsBinding
import com.vikmanz.shpppro.utilits.log
import com.vikmanz.shpppro.utilits.setContactPhoto
import com.vikmanz.shpppro.utilits.setContactPhotoFromUri


/**
 * Dialog Fragment in which user can add new contact with typed information to list of contacts.
 */
class AddContactDialogFragment : DialogFragment() {

    /**
     * Create ViewModel for this dialog.
     */
    private val viewModel: AddContactDialogFragmentViewModel by viewModels {
        AddContactDialogFragmentViewModelFactory(App.contactsService)
    }

    /**
     * Binding of that Dialog Fragment.
     */
    private lateinit var _binding: AddContactActivityMyContactsBinding


    /**
     * Create dialog fragment.
     */
    @SuppressLint("UseGetLayoutInflater")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

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
                buttonAddcontactAvatarFromGalery.setOnClickListener { requestImageFromGallery() }

                // set listener for create new contact and send it to MyContactsActivity
                buttonAddcontactSaveUser.setOnClickListener {
                    dialog?.dismiss()
                    viewModel.createNewContact(
                        name = textinputAddcontactUserNameInputfield.text.toString(),
                        career = textinputAddcontactUserCareerInputfield.text.toString(),
                        email = textinputAddcontactUserEmailInputfield.text.toString(),
                        phone = textinputAddcontactUserPhoneInputfield.text.toString(),
                        address = textinputAddcontactUserAddressInputfield.text.toString(),
                        birthday = textinputAddcontactUserBirthdayInputfield.text.toString()
                    )
                }

                // set listener for back button
                buttonAddcontactButtonBack.setOnClickListener { dialog?.cancel() }
            }

            // Set view and create dialog
            builder.setView(_binding.root).create()

        } ?: throw IllegalStateException("Activity cannot be null")
    }

    /**
     * Get fake image.
     */
    private fun requestDefaultImage() {
        viewModel.contactsService.incrementPhotoCounter()
//        if (viewModel.imgUri == Uri.EMPTY) viewModel.imgUri = Uri.EMPTY
        updateAvatarImage()
    }

    /**
     * Start activity with request image from gallery.
     */
    private fun requestImageFromGallery() {
        requestImageLauncher.launch(REQUEST_IMAGE_FROM_GALLERY)
    }

    /**
     * Register activity for result for request image from gallery.
     */
    private val requestImageLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                viewModel.imgUri = uri
                updateAvatarImage()
            }
        }

    /**
     * Update image in UV.
     */
    private fun updateAvatarImage() {
        if (viewModel.imgUri != Uri.EMPTY) {
            _binding.imageviewAddcontactAvatar.setContactPhotoFromUri(viewModel.imgUri)
            log("img update - $viewModel.imgUri")
        } else {
            _binding.imageviewAddcontactAvatar.setContactPhoto(viewModel.getFakePhotoUrl())
            log("img update -default")
        }
    }


    /**
     * Constants.
     */
    companion object {
        // input for request image launcher.
        private const val REQUEST_IMAGE_FROM_GALLERY = "image/*"
    }
}