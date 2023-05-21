package com.vikmanz.shpppro.di

import com.vikmanz.shpppro.data.contact_model.Contact
import com.vikmanz.shpppro.data.repository.ContactsRepository
import com.vikmanz.shpppro.data.repository.interfaces.Repository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindRepository(
        repository: ContactsRepository
    ): Repository<Contact>

}