package com.vikmanz.shpppro.domain.usecases

import com.vikmanz.shpppro.common.model.Account
import com.vikmanz.shpppro.domain.repository.ShPPRepositoryNet
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ua.digitalminds.fortrainerapp.data.result.ApiResult
import javax.inject.Inject

class RefreshTokenUseCase @Inject constructor(
    private val shPPRepositoryNet: ShPPRepositoryNet
) {

    operator fun invoke(oldAccount: Account): Flow<ApiResult<Account>> = flow {
        emit(ApiResult.Loading)
        emit(shPPRepositoryNet.refreshToken(
                oldAccount = oldAccount
            )
        )
    }
}