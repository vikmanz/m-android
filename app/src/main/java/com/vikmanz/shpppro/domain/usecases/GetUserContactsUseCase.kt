package com.vikmanz.shpppro.domain.usecases

import com.vikmanz.shpppro.common.model.User
import com.vikmanz.shpppro.common.result.UseCaseResult
import com.vikmanz.shpppro.domain.repository.ShPPRepositoryNet
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetUserContactsUseCase @Inject constructor(
        private val shPPRepositoryNet: ShPPRepositoryNet
) {

    operator fun invoke(token: String, userId: Int, contactId: Int): Flow<UseCaseResult<List<User>>> = flow {
        emit(UseCaseResult.Loading())
        emit(shPPRepositoryNet.getUserContacts(
            token = token,
            userId = userId,
            contactId = contactId
        ))
    }

}