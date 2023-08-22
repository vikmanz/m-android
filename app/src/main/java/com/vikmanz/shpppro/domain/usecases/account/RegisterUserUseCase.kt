package com.vikmanz.shpppro.domain.usecases.account

import com.vikmanz.shpppro.common.model.Account
import com.vikmanz.shpppro.domain.repository.ShPPAccountRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ua.digitalminds.fortrainerapp.data.result.ApiResult
import javax.inject.Inject

class RegisterUserUseCase @Inject constructor(
        private val accountRepository: ShPPAccountRepository
) {

    operator fun invoke(email: String, password: String): Flow<ApiResult<Account>> = flow {
        emit(ApiResult.Loading)
        emit(accountRepository.registerUser(
                email = email,
                password = password
        ))
    }
}