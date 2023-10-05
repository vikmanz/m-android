package com.vikmanz.shpppro.di.repository

import com.vikmanz.shpppro.data.repository.ShPPAccountRepositoryImpl
import com.vikmanz.shpppro.data.repository.ShPPContactsRepositoryImpl
import com.vikmanz.shpppro.domain.repository.ShPPAccountRepository
import com.vikmanz.shpppro.domain.repository.ShPPContactsRepository
import com.vikmanz.shpppro.domain.repository.ShPPUserContactsList
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Suppress("unused")
@Module
@InstallIn(SingletonComponent::class)
abstract class NetRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindShPPAccountRepository(
        accountRepository: ShPPAccountRepositoryImpl
    ): ShPPAccountRepository

    @Binds
    @Singleton
    abstract fun bindShPPContactsRepository(
        contactsRepository: ShPPContactsRepositoryImpl
    ): ShPPContactsRepository

}