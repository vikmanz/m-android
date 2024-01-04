package com.vikmanz.shpppro.domain.usecases.contacts

import com.vikmanz.shpppro.data.model.contact_item.ContactItem
import com.vikmanz.shpppro.data.repository.contacts.ShPPContactsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ua.digitalminds.fortrainerapp.data.result.ApiResult
import javax.inject.Inject

class GetUserContactsUseCase @Inject constructor(
    private val contactsRepository: ShPPContactsRepository,
) {

    operator fun invoke(): Flow<ApiResult<List<*>>> = flow {
        emit(ApiResult.Loading)
        emit(contactsRepository.getUserContacts())
    }

}