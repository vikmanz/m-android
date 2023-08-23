package com.vikmanz.shpppro.domain.usecases.account

import com.vikmanz.shpppro.common.model.Account
import com.vikmanz.shpppro.domain.repository.ShPPAccountRepository
import javax.inject.Inject

class GetAccountUseCase @Inject constructor(
    private val accountRepository: ShPPAccountRepository
) {
    operator fun invoke(): Account = accountRepository.account

}