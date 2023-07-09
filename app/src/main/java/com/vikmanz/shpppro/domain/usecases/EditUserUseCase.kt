package com.vikmanz.shpppro.domain.usecases

import com.vikmanz.shpppro.common.model.User
import com.vikmanz.shpppro.common.result.UseCaseResult
import com.vikmanz.shpppro.domain.repository.ShPPRepositoryNet
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class EditUserUseCase @Inject constructor(
        private val shPPRepositoryNet: ShPPRepositoryNet
) {

    operator fun invoke(token: String, user: User): Flow<UseCaseResult<User>> = flow {
        emit(UseCaseResult.Loading())
        emit(shPPRepositoryNet.editUser(
            token = token,
            user = user
        ))
    }
}