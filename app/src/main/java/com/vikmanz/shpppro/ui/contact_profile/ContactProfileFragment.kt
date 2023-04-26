package com.vikmanz.shpppro.ui.contact_profile

import android.net.Uri
import android.os.Bundle
import com.example.fragmentsnavigatortest.screens.edit.ContactProfileViewModel
import com.vikmanz.shpppro.App
import com.vikmanz.shpppro.databinding.FragmentContactProfileBinding
import com.vikmanz.shpppro.ui.base.BaseArgs
import com.vikmanz.shpppro.ui.base.BaseFragment
import com.vikmanz.shpppro.ui.base.screenViewModel
import com.vikmanz.shpppro.utilits.setContactPhoto
import com.vikmanz.shpppro.utilits.setContactPhotoFromUri

class ContactProfileFragment :
    BaseFragment<FragmentContactProfileBinding, ContactProfileViewModel>(
        FragmentContactProfileBinding::inflate
    ) {
    class CustomArgs(
        val contactID: Long
    ) : BaseArgs

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