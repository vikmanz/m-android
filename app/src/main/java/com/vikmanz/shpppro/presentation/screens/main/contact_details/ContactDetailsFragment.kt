package com.vikmanz.shpppro.presentation.screens.main.contact_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.vikmanz.shpppro.databinding.FragmentContactDetailsBinding
import com.vikmanz.shpppro.presentation.base.BaseFragment
import com.vikmanz.shpppro.presentation.utils.extensions.setImageWithGlide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ContactDetailsFragment :
    BaseFragment<FragmentContactDetailsBinding, ContactDetailsViewModel>(
        FragmentContactDetailsBinding::inflate
    ) {

    override val viewModel: ContactDetailsViewModel by viewModels()

    override fun onReady() {
        observeUserData()

    }

    private fun observeUserData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.user.collect { user ->
                    with(binding) {
                        textViewContactDetailsPersonName.text = user.name
                        textViewContactDetailsPersonCareer.text = user.career
                        textViewContactDetailsPersonAddress.text = user.email
                        imageViewContactDetailsAvatarImage.setImageWithGlide()
                    }
                }
            }
        }
    }


    override fun setListeners() {
        binding.buttonContactDetailsBackButton.setOnClickListener { viewModel.onButtonBackPressed() }
    }
}