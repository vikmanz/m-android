package com.vikmanz.shpppro.di.repository

import com.vikmanz.shpppro.domain.repository.ShPPAccountRepository
import com.vikmanz.shpppro.domain.repository.ShPPContactsRepository
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
    abstract fun bindShPPRepositoryNet(
        accountRepository: ShPPAccountRepository
    ): ShPPAccountRepository

    @Binds
    @Singleton
    abstract fun bindShPPRepositoryNet(
        contactsRepository: ShPPContactsRepository
    ): ShPPContactsRepository

}