package com.vikmanz.shpppro.presentation.main.my_contacts_list.add_contact

import android.app.Dialog
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.vikmanz.shpppro.R
import com.vikmanz.shpppro.databinding.FragmentAddContactMyContactsBinding

import com.vikmanz.shpppro.utilits.extensions.log
import com.vikmanz.shpppro.presentation.utils.extensions.setContactPhoto
import com.vikmanz.shpppro.presentation.utils.extensions.setContactPhotoFromUri


/**
 * Dialog Fragment in which user can add new contact with typed information to list of contacts.
 */
class AddContactDialogFragment : DialogFragment() {


    private val viewModel: AddContactDialogFragmentViewModel by viewModels()


    /**
     * Binding of that Dialog Fragment.
     */
    private var _binding: FragmentAddContactMyContactsBinding? = null


    /**
     * Create dialog fragment.
     */
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        // inflate binding of dialog fragment
        _binding = FragmentAddContactMyContactsBinding.inflate(layoutInflater)

        return activity?.let {

            // create dialog
            val builder = AlertDialog.Builder(it)

            _binding?.apply {
                // set avatar image
                updateAvatarImage()

                // set listener for change fake image and choose image from gallery
                imageViewAddContactAvatar.setOnClickListener { requestDefaultImage() }
                buttonAddContactGetAvatarFromGallery.setOnClickListener { requestImageFromGallery() }

                // set listener for create new contact and send it to MyContactsActivity
                buttonAddContactSaveUser.setOnClickListener {
                    if (checkContactIsNotEmpty()) {
                        dialog?.dismiss()
                        viewModel.createNewContact(
                            name = textInputAddContactUserNameInputField.text.toString(),
                            career = textInputAddContactUserCareerInputField.text.toString(),
                            email = textInputAddContactUserEmailInputField.text.toString(),
                            phone = textInputAddContactUserPhoneInputField.text.toString(),
                            address = textInputAddContactUserAddressInputField.text.toString(),
                            birthday = textInputAddContactUserBirthdayInputField.text.toString()
                        )
                    }
                }

                // set listener for back button
                buttonAddContactButtonBack.setOnClickListener { dialog?.cancel() }
            }

            // Set view and create dialog
            builder.setView(_binding?.root).create()

        } ?: throw IllegalStateException("Activity cannot be null")
    }

    private fun checkContactIsNotEmpty(): Boolean {
        return if (_binding?.textInputAddContactUserNameInputField?.text.toString().isEmpty()) {
            Toast.makeText(requireContext(),getString(R.string.my_contacts_add_contact_please_write_name),Toast.LENGTH_SHORT).show()
            false
        } else true
    }

    /**
     * Get fake image.
     */
    private fun requestDefaultImage() {
        viewModel.contactsRepository.incrementPhotoCounter()
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
            _binding?.imageViewAddContactAvatar?.setContactPhotoFromUri(viewModel.imgUri)
            log("img update - $viewModel.imgUri")
        } else {
            _binding?.imageViewAddContactAvatar?.setContactPhoto(viewModel.getFakePhotoUrl())
            log("img update -default")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * Constants.
     */
    companion object {
        // input for request image launcher.
        private const val REQUEST_IMAGE_FROM_GALLERY = "image/*"
    }
}