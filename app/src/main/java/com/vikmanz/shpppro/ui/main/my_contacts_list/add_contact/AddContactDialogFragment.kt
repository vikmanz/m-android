package com.vikmanz.shpppro.ui.main.my_contacts_list.add_contact

import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.vikmanz.shpppro.R
import com.vikmanz.shpppro.databinding.FragmentAddContactMyContactsBinding
import com.vikmanz.shpppro.ui.utils.extensions.setImageWithGlide
import dagger.hilt.android.AndroidEntryPoint

/**
 * Dialog Fragment in which user can add new contact with typed information to list of contacts.
 */
@AndroidEntryPoint
class AddContactDialogFragment : DialogFragment() {

    private val viewModel: AddContactDialogFragmentViewModel by viewModels()

    /**
     * Binding of that Dialog Fragment.
     */
    private var binding: FragmentAddContactMyContactsBinding? = null

    /**
     * Observer var for nulling it when destroy view.
     */
    private var avatarObserver: Observer<Any>? = null

    /**
     * Register activity for result for request image from gallery.
     */
    private val requestImageLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let { viewModel.setPhotoUri(uri) }
        }

    /**
     * Create dialog fragment.
     */
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        // inflate binding of dialog fragment
        binding = FragmentAddContactMyContactsBinding.inflate(layoutInflater)
        setAvatarObserver()

        return activity?.let {

            // create dialog
            val builder = AlertDialog.Builder(it)

            binding?.apply {

                // set listener for change fake image and choose image from gallery
                imageViewAddContactAvatar.setOnClickListener { changeFakePhotoToNext() }
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
            builder.setView(binding?.root).create()

        } ?: throw IllegalStateException("Activity cannot be null")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
        avatarObserver = null
    }

    private fun setAvatarObserver() = with(viewModel.currentPhoto) {
        avatarObserver = Observer<Any> {
            value?.let {
                binding?.imageViewAddContactAvatar?.setImageWithGlide(value!!)
            }
        }.also { observe(this@AddContactDialogFragment, it) }
    }

    private fun checkContactIsNotEmpty(): Boolean {
        return if (binding?.textInputAddContactUserNameInputField?.text.toString().isEmpty()) {
            Toast.makeText(
                requireContext(),
                getString(R.string.my_contacts_add_contact_please_write_name),
                Toast.LENGTH_SHORT
            ).show()
            false
        } else true
    }

    /**
     * Get fake image.
     */
    private fun changeFakePhotoToNext() {
        viewModel.changeFakePhotoToNext()
    }

    /**
     * Start activity with request image from gallery.
     */
    private fun requestImageFromGallery() {
        requestImageLauncher.launch(REQUEST_IMAGE_FROM_GALLERY)
    }

    /**
     * Constants.
     */
    companion object {
        // input for request image launcher.
        private const val REQUEST_IMAGE_FROM_GALLERY = "image/*"
    }
}