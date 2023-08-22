package com.vikmanz.shpppro.domain.usecases.contacts

import com.vikmanz.shpppro.common.model.User
import com.vikmanz.shpppro.domain.repository.ShPPAccountRepository
import com.vikmanz.shpppro.domain.repository.ShPPContactsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ua.digitalminds.fortrainerapp.data.result.ApiResult
import javax.inject.Inject

class GetAllUsersUseCase @Inject constructor(
        private val contactsRepository: ShPPContactsRepository,
        private val accRepository: ShPPAccountRepository
) {

    operator fun invoke(): Flow<ApiResult<List<User>>> = flow {
        emit(ApiResult.Loading)
        emit(contactsRepository.getAllUsers(
            user = accRepository.account.user,
        ))
    }

}