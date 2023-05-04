package com.vikmanz.shpppro.presentation.main.contact_details

import android.net.Uri
import com.vikmanz.shpppro.databinding.FragmentContactDetailsBinding
import com.vikmanz.shpppro.presentation.base.BaseArgument
import com.vikmanz.shpppro.presentation.base.BaseFragment
import com.vikmanz.shpppro.presentation.utils.screenViewModel
import com.vikmanz.shpppro.presentation.utils.extensions.setContactPhoto
import com.vikmanz.shpppro.presentation.utils.extensions.setContactPhotoFromUri

class ContactDetailsFragment :
    BaseFragment<FragmentContactDetailsBinding, ContactDetailsViewModel>(
        FragmentContactDetailsBinding::inflate
    ) {
    class CustomArgument(
//        override val name: String,
        val contactID: Long
    ) : BaseArgument

    override val viewModel by screenViewModel()

    override fun setStartUi() {
        with(binding){
            textviewMainPersonName.text = viewModel.name
            textviewMainPersonCareer.text = viewModel.career
            textviewMainPersonAddress.text = viewModel.homeAddress
            if (viewModel.photoUri == Uri.EMPTY) {
                imageviewMainAvatarImage.setContactPhoto(viewModel.photoUrl)
            } else {
                imageviewMainAvatarImage.setContactPhotoFromUri(viewModel.photoUri)
            }
        }
    }

    override fun setListeners() {
        binding.buttonContactBack.setOnClickListener { viewModel.onButtonBackPressed() }
    }
    override fun setObservers() { }

}