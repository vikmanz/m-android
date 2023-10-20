package com.vikmanz.shpppro.di

import com.vikmanz.shpppro.data.repository.account.ShPPAccountRepositoryImpl
import com.vikmanz.shpppro.data.repository.contacts.ShPPContactsRepositoryImpl
import com.vikmanz.shpppro.data.repository.account.ShPPAccountRepository
import com.vikmanz.shpppro.data.repository.contacts.ShPPContactsRepository
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