package com.vikmanz.shpppro.domain.usecases.contacts

import com.vikmanz.shpppro.data.model.contact_item.ContactItem
import com.vikmanz.shpppro.data.repository.contacts.ShPPContactsRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ua.digitalminds.fortrainerapp.data.result.ApiResult
import javax.inject.Inject

class DeleteContactUseCase @Inject constructor(
    private val contactsRepository: ShPPContactsRepository,
) {

    operator fun invoke(contactId: Int): Flow<ApiResult<List<ContactItem>>> = flow {
        emit(ApiResult.Loading)
        emit(contactsRepository.deleteContact(
            contactId = contactId
        ))
    }

}