package com.vikmanz.shpppro.di.repository

import com.vikmanz.shpppro.data.repository.ContactsRepositoryLocalImpl
import com.vikmanz.shpppro.domain.repository.ContactsRepositoryLocal
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Suppress("unused")
@Module
@InstallIn(SingletonComponent::class)
object LocalRepositoryModule {

    @Singleton
    @Provides
    fun bindRepository(): ContactsRepositoryLocal = ContactsRepositoryLocalImpl()

}