//package com.vikmanz.shpppro.ui.contactprofile
//
//import android.os.Bundle
//import com.example.fragmentsnavigatortest.screens.edit.ContactProfileViewModel
//import com.vikmanz.shpppro.App
//import com.vikmanz.shpppro.databinding.FragmentContactProfileBinding
//import com.vikmanz.shpppro.ui.base.BaseFragment
//import com.vikmanz.shpppro.ui.base.screenViewModel
//
//
//class ContactProfileFragment :
//    BaseFragment<FragmentContactProfileBinding, ContactProfileViewModel>(
//        FragmentContactProfileBinding::inflate
//    ) {
//
//    /**
//     * Create service for create new contacts. It sends to Add new contact Dialog Fragment.
//     */
//    private val contactsService = App.contactsReposetory
//
//    /**
//     * Create ViewModel for this activity.
//     */
//    override val viewModel by screenViewModel()
//
//    override fun setStartUI() {
//        TODO("Not yet implemented")
//    }
//
//    override fun setObservers() {
//        TODO("Not yet implemented")
//    }
//
//    override fun setListeners() {
//        TODO("Not yet implemented")
//    }
//
//    override fun onReady(savedInstanceState: Bundle?) {
//        TODO("Not yet implemented")
//    }
//}