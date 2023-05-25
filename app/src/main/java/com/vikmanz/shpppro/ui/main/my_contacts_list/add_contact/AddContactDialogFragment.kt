package com.vikmanz.shpppro.ui.main.my_contacts_list.add_contact

import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.vikmanz.shpppro.R
import com.vikmanz.shpppro.databinding.FragmentAddContactMyContactsBinding
import com.vikmanz.shpppro.ui.utils.extensions.setImageWithGlide
import com.vikmanz.shpppro.utilits.extensions.log
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
    private val _binding by lazy { requireNotNull(binding) } // Only for don't writing "?."

    /**
     * Register activity for result for request image from gallery.
     */
    private val requestImageLauncher =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
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

            with(_binding) {

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
            builder.setView(_binding.root).create()

        } ?: throw IllegalStateException("Activity cannot be null")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun setAvatarObserver() {
        log("set observer")
        viewModel.currentPhoto.observe(this@AddContactDialogFragment) {
            log("it = $it")
            log("binding = $_binding")
            _binding.imageViewAddContactAvatar.setImageWithGlide(it)
        }
    }

    private fun checkContactIsNotEmpty(): Boolean {
        return if (_binding.textInputAddContactUserNameInputField.text.toString().isEmpty()) {
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
        val request = PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
        requestImageLauncher.launch(request)
    }

}