package com.vikmanz.shpppro.domain.usecases.account

import com.vikmanz.shpppro.data.repository.account.ShPPAccountRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ua.digitalminds.fortrainerapp.data.result.ApiResult
import javax.inject.Inject

class AuthorizeUserUseCase @Inject constructor(
    private val accountRepository: ShPPAccountRepository
) {

    operator fun invoke(email: String, password: String): Flow<ApiResult<Boolean>> = flow {
        emit(ApiResult.Loading)
        emit(accountRepository.authorizeUser(
                email = email,
                password = password
        ))
    }
}