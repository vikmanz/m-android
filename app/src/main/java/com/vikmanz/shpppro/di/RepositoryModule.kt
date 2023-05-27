package com.vikmanz.shpppro.di

import com.vikmanz.shpppro.data.contact_model.ContactListItem
import com.vikmanz.shpppro.data.repository.ContactsRepositoryImpl
import com.vikmanz.shpppro.data.repository.interfaces.Repository
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
        repository: ContactsRepositoryImpl
    ): Repository<ContactListItem>

}