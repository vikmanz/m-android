package com.vikmanz.shpppro.domain.usecases.account

import com.vikmanz.shpppro.data.model.User
import com.vikmanz.shpppro.data.repository.account.ShPPAccountRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ua.digitalminds.fortrainerapp.data.result.ApiResult
import javax.inject.Inject

class EditUserUseCase @Inject constructor(
        private val accountRepository: ShPPAccountRepository
) {

    operator fun invoke(
        name: String? = null,
        phone: String? = null,
        address: String? = null,
        career: String? = null,
        birthday:String? = null,
        facebook: String? = null,
        instagram: String? = null,
        twitter: String? = null,
        linkedin: String? = null,
    ): Flow<ApiResult<User>> = flow {
        emit(ApiResult.Loading)
        emit(accountRepository.editUser(
            name = name,
            phone = phone,
            address = address,
            career = career,
            birthday = birthday,
            facebook = facebook,
            instagram = instagram,
            twitter = twitter,
            linkedin = linkedin
        ))
    }
}