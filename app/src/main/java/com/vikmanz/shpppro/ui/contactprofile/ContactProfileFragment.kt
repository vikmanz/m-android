package com.example.fragmentsnavigatortest.screens.edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.fragmentsnavigatortest.databinding.FragmentEditBinding
import com.example.fragmentsnavigatortest.screens.base.BaseScreen
import com.vikmanz.shpppro.ui.base.BaseFragment

class ContactProfileFragment : BaseFragment() {

    // this screen accepts a string value from the HelloFragment
    class Screen(override val initialValue: String) : BaseScreen {
        override val name: String = "edit"
    }

    override val viewModel by screenViewModel<ContactProfileViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding = FragmentEditBinding.inflate(inflater, container, false)

        // listening for the initial value to be assigned into the valueEditText
        viewModel.initialMessageEvent.observe(viewLifecycleOwner) {
            it.getValue()?.let { message -> binding.valueEditText.setText(message) }
        }

        // buttons' click listeners
        binding.saveButton.setOnClickListener {
            viewModel.onSavePressed(binding.valueEditText.text.toString())
        }
        binding.cancelButton.setOnClickListener {
            viewModel.onCancelPressed()
        }

        return binding.root
    }
}