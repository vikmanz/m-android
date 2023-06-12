package com.vikmanz.shpppro.data.di

import com.vikmanz.shpppro.data.model.ContactListItem
import com.vikmanz.shpppro.data.repository.LocalContactsRepository
import com.vikmanz.shpppro.data.repository.interfaces.ContactsRepository
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
        repository: LocalContactsRepository
    ): ContactsRepository<ContactListItem>

}