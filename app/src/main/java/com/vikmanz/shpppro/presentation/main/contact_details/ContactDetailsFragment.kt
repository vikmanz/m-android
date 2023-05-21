package com.vikmanz.shpppro.presentation.main.contact_details

import androidx.fragment.app.viewModels
import com.vikmanz.shpppro.databinding.FragmentContactDetailsBinding
import com.vikmanz.shpppro.presentation.base.BaseFragment
import com.vikmanz.shpppro.presentation.utils.extensions.setImageWithGlide

class ContactDetailsFragment :
    BaseFragment<FragmentContactDetailsBinding>(
        FragmentContactDetailsBinding::inflate
    ) {

    private val viewModel by viewModels<ContactDetailsViewModel>()

    override fun onCreatedFragmentView() {
        with(binding) {
            textViewContactDetailsPersonName.text = viewModel.name
            textViewContactDetailsPersonCareer.text = viewModel.career
            textViewContactDetailsPersonAddress.text = viewModel.homeAddress
            imageViewContactDetailsAvatarImage.setImageWithGlide(viewModel.photoLink)
        }
    }

    override fun setListeners() {
        binding.buttonContactDetailsBackButton.setOnClickListener { viewModel.onButtonBackPressed() }
    }
}