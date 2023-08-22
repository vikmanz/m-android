package com.vikmanz.shpppro.domain.usecases.contacts

import com.vikmanz.shpppro.common.model.User
import com.vikmanz.shpppro.domain.repository.ShPPContactsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ua.digitalminds.fortrainerapp.data.result.ApiResult
import javax.inject.Inject

class GetUserContactsUseCase @Inject constructor(
        private val contactsRepository: ShPPContactsRepository
) {

    operator fun invoke(token: String, userId: Int, contactId: Int): Flow<ApiResult<List<User>>> = flow {
        emit(ApiResult.Loading)
        emit(contactsRepository.getUserContacts(
            token = token,
            userId = userId,
            contactId = contactId
        ))
    }

}