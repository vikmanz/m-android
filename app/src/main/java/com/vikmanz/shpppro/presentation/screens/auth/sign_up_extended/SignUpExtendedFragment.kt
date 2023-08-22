package com.vikmanz.shpppro.presentation.screens.auth.sign_up_extended

import androidx.fragment.app.viewModels
import com.vikmanz.shpppro.R
import com.vikmanz.shpppro.databinding.FragmentSignUpExtendedBinding
import com.vikmanz.shpppro.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpExtendedFragment :
    BaseFragment<FragmentSignUpExtendedBinding, SignUpExtendedViewModel>(
        FragmentSignUpExtendedBinding::inflate
    ) {

    override val viewModel: SignUpExtendedViewModel by viewModels()

    override fun setListeners() {
        with(binding) {
            // buttonLoginRegisterByEmail.setOnClickListener { checkForm() }
            // textViewLoginSwitchScreenToLoginButton.setOnClickListener { viewModel.swapLoginAndRegister() }
            // buttonLoginRegisterByGoogle.setOnClickListener { buttonLoginRegisterByGoogle.setFunText() }

            buttonSignUpExtSaveUser.setOnClickListener {
                val username = textInputSignUpExtUserNameField.text.toString()
                val phone = textInputSignUpExtUserPhoneField.text.toString()
                viewModel.onSaveClick(username, phone)
            }

            buttonSignUpExtCancel.setOnClickListener { viewModel.navigateBack() }
        }

//        backgroundFocusHandler()                // de-focus fields when click on bg


    }




    /**
     *  De-focus views, when user do click on background.
     */
    private fun backgroundFocusHandler() = with(binding) {
//        root.setOnClickListener {
//            textInputLoginEmailField.clearFocus()
//            textInputLoginPasswordField.clearFocus()
//            hideKeyboard(it)
//        }
    }

}