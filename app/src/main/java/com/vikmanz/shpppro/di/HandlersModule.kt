package com.vikmanz.shpppro.di

import com.vikmanz.shpppro.data.holders.user_contact_list.ContactsListHolder
import com.vikmanz.shpppro.data.holders.user_token.UserTokenHolder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Suppress("unused")
@Module
@InstallIn(SingletonComponent::class)
object HandlersModule {

    @Singleton
    @Provides
    fun providesUserTokenHandler(): UserTokenHolder = UserTokenHolder

    @Singleton
    @Provides
    fun providesContactsListHolder(): ContactsListHolder = ContactsListHolder


}