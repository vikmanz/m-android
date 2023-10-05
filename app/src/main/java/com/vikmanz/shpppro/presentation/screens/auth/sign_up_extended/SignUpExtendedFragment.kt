package com.vikmanz.shpppro.presentation.screens.auth.sign_up_extended

import androidx.fragment.app.viewModels
import com.vikmanz.shpppro.databinding.FragmentSignUpExtendedBinding
import com.vikmanz.shpppro.presentation.base.BaseFragment
import com.vikmanz.shpppro.presentation.utils.extensions.setKeyboardVisibility
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpExtendedFragment :
    BaseFragment<FragmentSignUpExtendedBinding, SignUpExtendedViewModel>(
        FragmentSignUpExtendedBinding::inflate
    ) {

    override val viewModel: SignUpExtendedViewModel by viewModels()

    override fun setListeners() {
        with(binding) {
            buttonSignUpExtSaveUser.setOnClickListener {
                val username = textInputSignUpExtUserNameField.text.toString()
                val phone = textInputSignUpExtUserPhoneField.text.toString()
                viewModel.onSaveClick(username, phone)
            }
            buttonSignUpExtCancel.setOnClickListener { viewModel.onCancelClick() }
        }
        backgroundFocusHandler()                // de-focus fields when click on bg
    }

    /**
     *  De-focus views, when user do click on background.
     */
    private fun backgroundFocusHandler() = with(binding) {
        root.setOnClickListener {
            textInputSignUpExtUserNameField.clearFocus()
            textInputSignUpExtUserPhoneField.clearFocus()
            setKeyboardVisibility(false)
        }
    }

}