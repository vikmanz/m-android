package com.vikmanz.shpppro.domain.usecases

import com.vikmanz.shpppro.common.model.Account
import com.vikmanz.shpppro.common.result.UseCaseResult
import com.vikmanz.shpppro.domain.repository.ShPPRepositoryNet
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RefreshTokenUseCase @Inject constructor(
    private val shPPRepositoryNet: ShPPRepositoryNet
) {

    operator fun invoke(oldAccount: Account): Flow<UseCaseResult<Account>> = flow {
        emit(UseCaseResult.Loading())
        emit(shPPRepositoryNet.refreshToken(
                oldAccount = oldAccount
            )
        )
    }
}