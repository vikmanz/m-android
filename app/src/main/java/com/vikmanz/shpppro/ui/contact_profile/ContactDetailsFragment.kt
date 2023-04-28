package com.vikmanz.shpppro.ui.contact_profile

import android.net.Uri
import com.vikmanz.shpppro.databinding.FragmentContactDetailsBinding
import com.vikmanz.shpppro.ui.base.BaseArgument
import com.vikmanz.shpppro.ui.base.BaseFragment
import com.vikmanz.shpppro.ui.base.screenViewModel
import com.vikmanz.shpppro.utilits.setContactPhoto
import com.vikmanz.shpppro.utilits.setContactPhotoFromUri

class ContactDetailsFragment :
    BaseFragment<FragmentContactDetailsBinding, ContactDetailsViewModel>(
        FragmentContactDetailsBinding::inflate
    ) {
    class CustomArgument(
        override val name: String, val contactID: Long
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