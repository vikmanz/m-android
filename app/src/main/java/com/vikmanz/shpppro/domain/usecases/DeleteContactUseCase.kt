package com.vikmanz.shpppro.domain.usecases

import com.vikmanz.shpppro.common.model.User
import com.vikmanz.shpppro.domain.repository.ShPPRepositoryNet
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ua.digitalminds.fortrainerapp.data.result.ApiResult
import javax.inject.Inject

class DeleteContactUseCase @Inject constructor(
        private val shPPRepositoryNet: ShPPRepositoryNet
) {

    operator fun invoke(token: String, userId: Int, contactId: Int): Flow<ApiResult<List<User>>> = flow {
        emit(ApiResult.Loading)
        emit(shPPRepositoryNet.deleteContact(
            token = token,
            userId = userId,
            contactId = contactId
        ))
    }

}