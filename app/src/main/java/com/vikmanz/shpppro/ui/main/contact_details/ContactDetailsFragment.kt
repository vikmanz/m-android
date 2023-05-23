package com.vikmanz.shpppro.ui.main.contact_details

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.vikmanz.shpppro.base.BaseFragmentWithArgs
import com.vikmanz.shpppro.databinding.FragmentContactDetailsBinding
import com.vikmanz.shpppro.ui.utils.extensions.setImageWithGlide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ContactDetailsFragment :
    BaseFragmentWithArgs<FragmentContactDetailsBinding, ContactDetailsViewModel, ContactDetailsFragmentArgs>(
        FragmentContactDetailsBinding::inflate
    ) {

    override val args: ContactDetailsFragmentArgs by navArgs()
    override val viewModel: ContactDetailsViewModel by viewModels()

    override fun onReady(savedInstanceState: Bundle?) {
        super.onReady(savedInstanceState)
        with(binding) {
            textViewContactDetailsPersonName.text = viewModel.contactName
            textViewContactDetailsPersonCareer.text = viewModel.contactCareer
            textViewContactDetailsPersonAddress.text = viewModel.contactAddress
            imageViewContactDetailsAvatarImage.setImageWithGlide(viewModel.contactPhoto)
        }
    }

    override fun setListeners() {
        binding.buttonContactDetailsBackButton.setOnClickListener { viewModel.onButtonBackPressed() }
    }
}