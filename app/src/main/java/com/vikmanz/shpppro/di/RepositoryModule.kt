package com.vikmanz.shpppro.di

import com.vikmanz.shpppro.data.model.ContactItem
import com.vikmanz.shpppro.data.repository.ContactsRepositoryLocalImpl
import com.vikmanz.shpppro.data.repository.ShPPRepositoryNetImpl
import com.vikmanz.shpppro.domain.repository.ContactsRepositoryLocal
import com.vikmanz.shpppro.domain.repository.ShPPRepositoryNet
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Suppress("unused")
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindRepository(
        repository: ContactsRepositoryLocalImpl
    ): ContactsRepositoryLocal<ContactItem>

    @Binds
    @Singleton
    abstract fun bindShPPRepositoryNet(
        authenticationRepository: ShPPRepositoryNetImpl
    ): ShPPRepositoryNet<ContactItem>

}