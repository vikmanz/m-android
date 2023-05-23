package com.vikmanz.shpppro.ui.main.contact_details

import com.vikmanz.shpppro.base.BaseViewModelWithArgs
import com.vikmanz.shpppro.data.contact_model.Contact
import com.vikmanz.shpppro.data.repository.interfaces.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ContactDetailsViewModel @Inject constructor(
    contactsRepository: Repository<Contact>
) : BaseViewModelWithArgs<ContactDetailsFragmentArgs>() {

    private val contact: Contact by lazy {
        contactsRepository.findContact(args.contactID)
            ?: contactsRepository.generateRandomContact()
    }

    val contactName by lazy { contact.contactName }
    val contactCareer by lazy { contact.contactCareer }
    val contactAddress by lazy { contact.contactAddress }
    val contactPhoto by lazy { contact.contactPhotoLink }

    fun onButtonBackPressed() {
        navigateBack()
    }


}