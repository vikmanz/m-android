package com.vikmanz.shpppro.ui.main.contact_details

import androidx.fragment.app.viewModels
import com.vikmanz.shpppro.base.BaseFragment
import com.vikmanz.shpppro.databinding.FragmentContactDetailsBinding
import com.vikmanz.shpppro.ui.utils.extensions.setImageWithGlide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ContactDetailsFragment :
    BaseFragment<FragmentContactDetailsBinding, ContactDetailsViewModel>(
        FragmentContactDetailsBinding::inflate
    ) {

    override val viewModel: ContactDetailsViewModel by viewModels()

    override fun initUI() {
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