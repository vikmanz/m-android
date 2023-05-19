package com.vikmanz.shpppro.presentation.main.contact_details

import com.vikmanz.shpppro.databinding.FragmentContactDetailsBinding
import com.vikmanz.shpppro.presentation.base.BaseArgument
import com.vikmanz.shpppro.presentation.base.BaseFragment
import com.vikmanz.shpppro.presentation.utils.extensions.setImageWithGlide
import com.vikmanz.shpppro.presentation.utils.screenMainViewModel

class ContactDetailsFragment :
    BaseFragment<FragmentContactDetailsBinding, ContactDetailsViewModel>(
        FragmentContactDetailsBinding::inflate
    ) {

    /**
     * Create ViewModel for this activity. Custom class need to change relevant type of viewModel in fabric.
     */
    class CustomArgument(
        val contactID: Long
    ) : BaseArgument
    override val viewModel by screenMainViewModel()

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