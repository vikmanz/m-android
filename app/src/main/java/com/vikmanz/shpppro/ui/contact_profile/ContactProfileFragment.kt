package com.vikmanz.shpppro.ui.contact_profile

import android.net.Uri
import android.os.Bundle
import com.vikmanz.shpppro.databinding.FragmentContactProfileBinding
import com.vikmanz.shpppro.ui.base.BaseArgument
import com.vikmanz.shpppro.ui.base.BaseFragment
import com.vikmanz.shpppro.ui.base.screenViewModel
import com.vikmanz.shpppro.utilits.setContactPhoto
import com.vikmanz.shpppro.utilits.setContactPhotoFromUri

class ContactProfileFragment :
    BaseFragment<FragmentContactProfileBinding, ContactProfileViewModel>(
        FragmentContactProfileBinding::inflate
    ) {
    class CustomArgument(
        override val name: String, val contactID: Long
    ) : BaseArgument

    override val viewModel by screenViewModel()

    override fun setListeners() {
        binding.buttonContactBack.setOnClickListener { viewModel.onBackPressed() }
    }
    override fun setObservers() { }
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
}